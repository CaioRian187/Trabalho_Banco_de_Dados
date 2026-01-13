const API_RECEITA = "http://localhost:8080/receita";
const API_CONSULTA = "http://localhost:8080/consulta";

document.getElementById('receita-form').addEventListener('submit', salvarReceita);

window.onload = async () => {
    await carregarConsultas();
    listarReceitas();
};

async function carregarConsultas() {
    try {
        const response = await fetch(API_CONSULTA);
        const consultas = await response.json();
        const select = document.getElementById('select-consulta');
        select.innerHTML = '<option value="" disabled selected>Selecione uma consulta...</option>';

        if(Array.isArray(consultas)){
            consultas.forEach(c => {
                const pacNome = c.paciente ? c.paciente.nome : 'Sem Paciente';
                let dataFormatada = "Data n/d";
                if(c.dataHora) {
                    // Formata Data BR
                    const dataObj = new Date(c.dataHora);
                    dataFormatada = dataObj.toLocaleDateString('pt-BR') + " " + dataObj.toLocaleTimeString('pt-BR', {hour: '2-digit', minute:'2-digit'});
                }
                select.innerHTML += `<option value="${c.id}">${dataFormatada} - ${pacNome}</option>`;
            });
        }
    } catch (e) { console.error(e); }
}

async function listarReceitas() {
    try {
        const response = await fetch(API_RECEITA);
        const receitas = await response.json();
        const tbody = document.getElementById('receita-table-body');
        tbody.innerHTML = "";

        if (!Array.isArray(receitas)) return;

        receitas.forEach(r => {
            const pacienteNome = r.consulta && r.consulta.paciente ? r.consulta.paciente.nome : 'N/A';

            // Tratamento Data BR
            let dataEmissaoStr = '-';
            if(r.dataEmissao) {
                // Se vier String "2026-02-14"
                if(typeof r.dataEmissao === 'string') {
                     const partes = r.dataEmissao.split('-');
                     dataEmissaoStr = `${partes[2]}/${partes[1]}/${partes[0]}`;
                }
                // Se vier Array [2026, 2, 14]
                else if(Array.isArray(r.dataEmissao)) {
                    const dia = r.dataEmissao[2].toString().padStart(2, '0');
                    const mes = r.dataEmissao[1].toString().padStart(2, '0');
                    const ano = r.dataEmissao[0];
                    dataEmissaoStr = `${dia}/${mes}/${ano}`;
                }
            }

            tbody.innerHTML += `
                <tr>
                    <td>${r.id}</td>
                    <td>${r.medicamento}</td>
                    <td>${r.dosagem}</td>
                    <td>${dataEmissaoStr}</td>
                    <td>${pacienteNome}</td>
                    <td>
                        <button class="btn-delete" onclick="excluirReceita(${r.id})">Excluir</button>
                    </td>
                </tr>`;
        });
    } catch (error) { console.error(error); }
}

async function salvarReceita(event) {
    event.preventDefault();
    const id = document.getElementById('receita-id').value;
    const consultaId = document.getElementById('select-consulta').value;

    if(!consultaId) { alert("Selecione uma consulta."); return; }

    const receita = {
        medicamento: document.getElementById('medicamento').value,
        dosagem: document.getElementById('dosagem').value,
        instrucoes: document.getElementById('instrucoes').value,
        consulta: { id: consultaId }
    };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_RECEITA}/${id}` : API_RECEITA;

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(receita)
        });
        if (response.ok) {
            alert("Receita salva!");
            resetForm();
            listarReceitas();
        }
    } catch (error) { console.error(error); }
}

async function excluirReceita(id) {
    if(confirm("Excluir?")) {
        await fetch(`${API_RECEITA}/${id}`, { method: 'DELETE' });
        listarReceitas();
    }
}

function resetForm() {
    document.getElementById('receita-form').reset();
    document.getElementById('receita-id').value = "";
}