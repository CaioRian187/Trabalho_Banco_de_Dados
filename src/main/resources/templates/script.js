const API_URL = "http://localhost:8080/medico";

document.getElementById('medico-form').addEventListener('submit', salvarMedico);
window.onload = listarTodos;

// Rota: GET /medico (findAllMedicos)
async function listarTodos() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Erro ao buscar dados");
        
        const medicos = await response.json();
        renderizarTabela(medicos);
    } catch (error) {
        console.error("Erro ao listar todos:", error);
        alert("Não foi possível carregar a lista de médicos.");
    }
}

// Rota: GET /medico/nome/{nome}
async function buscarPorNome() {
    const nome = document.getElementById('search-name').value;
    if (!nome) return listarTodos();

    try {
        const response = await fetch(`${API_URL}/nome/${nome}`);
        if (response.ok) {
            const medico = await response.json();
            renderizarTabela([medico]); // Coloca em array para a tabela processar
        } else {
            alert("Médico não encontrado.");
        }
    } catch (error) {
        console.error("Erro na busca:", error);
    }
}

// Rotas: POST (create) e PUT (update)
async function salvarMedico(event) {
    event.preventDefault();

    const id = document.getElementById('medico-id').value;
    const medicoData = {
        nome: document.getElementById('nome').value,
        crm: document.getElementById('crm').value,
        especialidade: document.getElementById('especialidade').value
    };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_URL}/${id}` : API_URL;

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(medicoData)
        });

        if (response.ok) {
            alert(id ? "Médico atualizado!" : "Médico cadastrado!");
            resetForm();
            listarTodos();
        }
    } catch (error) {
        console.error("Erro ao salvar:", error);
    }
}

// Rota: DELETE /{id}
async function excluirMedico(id) {
    if (!confirm("Deseja realmente excluir este médico?")) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (response.ok) {
            listarTodos();
        }
    } catch (error) {
        console.error("Erro ao excluir:", error);
    }
}

function renderizarTabela(medicos) {
    const tbody = document.getElementById('medico-table-body');
    tbody.innerHTML = "";

    medicos.forEach(m => {
        tbody.innerHTML += `
            <tr>
                <td>${m.id}</td>
                <td>${m.nome}</td>
                <td>${m.crm || '-'}</td>
                <td>${m.especialidade || '-'}</td>
                <td>
                    <button class="btn-edit" onclick="prepararEdicao(${m.id}, '${m.nome}', '${m.crm}', '${m.especialidade}')">Editar</button>
                    <button class="btn-delete" onclick="excluirMedico(${m.id})">Excluir</button>
                </td>
            </tr>
        `;
    });
}

function prepararEdicao(id, nome, crm, especialidade) {
    document.getElementById('medico-id').value = id;
    document.getElementById('nome').value = nome;
    document.getElementById('crm').value = crm;
    document.getElementById('especialidade').value = especialidade;
    
    document.getElementById('form-title').innerText = "Editar Médico";
    document.getElementById('btn-cancel').style.display = "inline";
}

function resetForm() {
    document.getElementById('medico-form').reset();
    document.getElementById('medico-id').value = "";
    document.getElementById('form-title').innerText = "Cadastrar Médico";
    document.getElementById('btn-cancel').style.display = "none";
}