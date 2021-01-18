let addButton = document.getElementById('addButton');
const keywordElement = document.getElementById('keyword');
const valElement = document.getElementById('value');
addButton.addEventListener('click', function () {
    localStorage.setItem(keywordElement.value, valElement.value);
});

window.addEventListener('storage', () => {
    // When local storage changes, dump the list to
    // the console.
    let localStorageData = '';
    for (i = 0; i < localStorage.length; i++) {
        localStorageData += '\n' + localStorage.key(i) + ' - ' + localStorage.getItem(localStorage.key(i)) + '\n';
    }
    alert(
        'LocalStorageChangeData:\n' + localStorageData
    );
});