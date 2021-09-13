function addNewItem() {
    const form = document.querySelector("#add_new")
    const data = {
        "description": form.querySelector('input[name = "description"]').value
    }

    const request = new XMLHttpRequest();
    request.open("post", "/todo/item.do")
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(JSON.stringify(data))
}


function removeChildren(tableBody) {
    while (tableBody.firstChild) {
        tableBody.removeChild(tableBody.firstChild)
    }
}

function populateTable(json) {
    const tableBody = document.querySelector("#items-table > tbody");
    removeChildren(tableBody);

    json.forEach((item) => {
            const tr = document.createElement("tr");

            const td_id = document.createElement("td");
            td_id.textContent = item.id;
            const td_desc = document.createElement("td");
            td_desc.textContent = item.description;
            const td_created = document.createElement("td");
            td_created.textContent = item.created;
            const checkBoxDone = document.createElement("INPUT");
            checkBoxDone.setAttribute("type", "checkbox");
            checkBoxDone.setAttribute("name", "city");
            checkBoxDone.setAttribute("value", "London");
            checkBoxDone.checked = item.done;
            checkBoxDone.onchange = event => {
                item.done = event.returnValue
                onChangeTaskDone(item);
            }

            tr.append(td_id, td_desc, td_created, checkBoxDone);
            tableBody.appendChild(tr);
        }
    )
}

function onChangeTaskDone(item) {
    const request = new XMLHttpRequest();
    request.open("post", "/todo/item.do")
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(JSON.stringify(item))
}

function loadItems(include_done) {
    const request = new XMLHttpRequest();
    request.set
    request.open("get", `/todo/item.do?shows_done=${include_done}`)
    request.onload = () => {
        try {
            const json = JSON.parse(request.responseText);
            populateTable(json);
        } catch (e) {
            console.warn('Could not load Items!')
        }
    }
    request.send();
}

document.addEventListener("DOMContentLoaded", () => {
    loadItems(document.querySelector("#check_done").checked);
})
