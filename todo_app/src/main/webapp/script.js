function addNewItem(userId) {
    console.log(`user id is ${userId}`)
    const form = document.querySelector("#add_new")
    let categoriesElem = form.querySelector("#category");
    let categories = Array.apply(null, categoriesElem.options).filter(x => x.selected).map(x => x.id);

    const data = {
        "description": form.querySelector('input[name = "description"]').value,
        "author": {"id": userId},
        "categories": categories.map(id => ({ id }))
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

            const td_categories = document.createElement("td");
            td_categories.textContent = item.categories.map(value => value.name).join(", ");

            const td_author = document.createElement("td");
            td_author.textContent = item.author.name;

            const td_created = document.createElement("td");
            td_created.textContent = item.created;
            const checkBoxDone = document.createElement("INPUT");
            checkBoxDone.setAttribute("type", "checkbox");
            checkBoxDone.checked = item.done;
            checkBoxDone.onchange = (event) => {
                item.done = checkBoxDone.checked
                onChangeTaskDone(item);
            }

            tr.append(td_id, td_desc, td_categories, td_author, td_created, checkBoxDone);
            tableBody.appendChild(tr);
        }
    )
}

function onChangeTaskDone(item) {
    const request = new XMLHttpRequest();
    request.open("post", "/todo/item.do")
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(JSON.stringify(item))
    request.onload = () => {
        refreshTableBody();
    }
}

function loadItems(include_done) {
    const request = new XMLHttpRequest();
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
    refreshTableBody();
    loadOptions();
});

function loadOptions() {
    const request = new XMLHttpRequest();
    request.open("get", `/todo/category.do`)
    request.onload = () => {
        try {
            const json = JSON.parse(request.responseText);
            let list = document.querySelector("#category");
            json.forEach((elem) => {
                    let option = document.createElement('option');
                    option.value = elem.name;
                    option.id = elem.id;
                    option.label = elem.name;
                    list.appendChild(option);
                }
            )
        } catch (e) {
            console.warn('Could not load Categories!')
        }
    }
    request.send();
}

function refreshTableBody() {
    loadItems(document.querySelector("#check_done").checked);
}
