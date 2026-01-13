const API_BASE = "http://localhost:8080/relatorio";
const API_MEDICO = "http://localhost:8080/medico";
const API_ESPECIALIDADE = "http://localhost:8080/especialidade";

window.onload = async () => {
    carregarMedicos();
    carregarEspecialidades();
};

// --- Carregamentos Iniciais (Para os Selects) ---
async function carregarMedicos() {
    try {
        const res = await fetch(API_MEDICO);
        const lista = await res.json();
        const sel = document.getElementById('select-medico-count');
        sel.innerHTML = '<option value="" disabled selected>Selecione um médico...</option>';
        if(Array.isArray(lista)) {
            lista.forEach(m => sel.innerHTML += `<option value="${m.id}">${m.nome}</option>`);
        }
    } catch(e) { console.error(e); }
}

async function carregarEspecialidades() {
    try {
        const res = await fetch(API_ESPECIALIDADE);
        const lista = await res.json();
        const sel = document.getElementById('select-especialidade-join');
        sel.innerHTML = '<option value="" disabled selected>Selecione uma especialidade...</option>';
        if(Array.isArray(lista)) {
            lista.forEach(e => sel.innerHTML += `<option value="${e.nome}">${e.nome}</option>`);
        }
    } catch(e) { console.error(e); }
}

// 1. MIN: Paciente Mais Idoso
async function buscarMaisIdoso() {
    try {
        const res = await fetch(`${API_BASE}/pacientes/mais-idoso`);
        if(res.ok) {
            const dataIso = await res.json(); // Retorna "yyyy-MM-dd"
            // Formatar Data
            const partes = dataIso.split('-');
            const dataBr = `${partes[2]}/${partes[1]}/${partes[0]}`;

            document.getElementById('res-min').innerText = `Nascimento: ${dataBr}`;
            document.getElementById('res-min').style.color = "green";
        } else {
            document.getElementById('res-min').innerText = "Nenhum dado encontrado.";
        }
    } catch(e) { console.error(e); }
}

// 2. MAX: Última Data de Consulta
async function buscarUltimaData() {
    try {
        const res = await fetch(`${API_BASE}/consultas/ultima-data`);
        if(res.ok) {
            const dataIso = await res.json();
            const dataObj = new Date(dataIso);
            const formatada = dataObj.toLocaleDateString('pt-BR') + ' às ' + dataObj.toLocaleTimeString('pt-BR');

            document.getElementById('res-max').innerText = formatada;
            document.getElementById('res-max').style.color = "green";
        } else {
            document.getElementById('res-max').innerText = "Nenhuma consulta futura.";
        }
    } catch(e) { console.error(e); }
}

// 3. COUNT: Contar Consultas por Médico
async function contarConsultas() {
    const idMedico = document.getElementById('select-medico-count').value;
    if(!idMedico) { alert("Selecione um médico!"); return; }

    try {
        const res = await fetch(`${API_BASE}/medico/${idMedico}/total-consultas`);
        const total = await res.json();
        document.getElementById('res-count').innerText = `${total} consulta(s) agendada(s)`;
    } catch(e) { console.error(e); }
}

// 4. LIKE: Buscar Paciente por Nome
async function buscarPacientesLike() {
    const termo = document.getElementById('termo-busca').value;
    const tbody = document.getElementById('table-like');
    tbody.innerHTML = "";

    try {
        const res = await fetch(`${API_BASE}/pacientes/busca?termo=${termo}`);
        const pacientes = await res.json();

        if(pacientes.length === 0) {
            tbody.innerHTML = "<tr><td colspan='4'>Nenhum paciente encontrado.</td></tr>";
            return;
        }

        pacientes.forEach(p => {
            tbody.innerHTML += `
                <tr>
                    <td>${p.id}</td>
                    <td>${p.nome}</td>
                    <td>${p.cpf}</td>
                    <td>${p.telefone}</td>
                </tr>`;
        });
    } catch(e) { console.error(e); }
}

// 5. JOIN: Consultas por Especialidade
async function buscarPorEspecialidade() {
    // Pegamos o TEXTO (Nome) da especialidade, pois o endpoint pede ?nome=...
    const select = document.getElementById('select-especialidade-join');
    const nomeEspecialidade = select.value;

    if(!nomeEspecialidade) { alert("Selecione uma especialidade!"); return; }

    const tbody = document.getElementById('table-join');
    tbody.innerHTML = "";

    try {
        const res = await fetch(`${API_BASE}/consultas/por-especialidade?nome=${nomeEspecialidade}`);
        const consultas = await res.json();

        if(consultas.length === 0) {
            tbody.innerHTML = "<tr><td colspan='4'>Nenhuma consulta para esta especialidade.</td></tr>";
            return;
        }

        consultas.forEach(c => {
            const dataObj = new Date(c.dataHora);
            const dataFmt = dataObj.toLocaleDateString('pt-BR') + ' ' + dataObj.toLocaleTimeString('pt-BR', {hour:'2-digit', minute:'2-digit'});

            tbody.innerHTML += `
                <tr>
                    <td>${dataFmt}</td>
                    <td>${c.medico.nome}</td>
                    <td>${c.paciente.nome}</td>
                    <td>${c.observacoes}</td>
                </tr>`;
        });
    } catch(e) { console.error(e); }
}