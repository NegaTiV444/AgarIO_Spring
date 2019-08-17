function loadRecords() {
    var url = window.location.href.substring(0, window.location.href.lastIndexOf("/") + 1) + "records";
    const request = new Request(url);
    fetch(request)
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            console.log(data);
            const recordBlock = createRecordBlock(data);
            const recordsListBlock = document.getElementById('records-list');
            recordsListBlock.appendChild(recordBlock);
        });
}

function createRecordBlock(jsonData) {
    console.log(jsonData);
    const recordBlock = document.createDocumentFragment();
    const recordItem = document.getElementById('record-item-template');
    var i = 0;
    jsonData.forEach(function () {
        const item = recordItem.content.cloneNode(true).querySelector('.record');
        const child = fillTemplate(item, jsonData[i++]);
        recordBlock.appendChild(child);
    });
    return recordBlock;
}

function fillTemplate(item, jsonData) {
    item.querySelector('.name').textContent = jsonData.name + " : ";
    item.querySelector('.score').textContent = jsonData.score;
    return item;
}

loadRecords();