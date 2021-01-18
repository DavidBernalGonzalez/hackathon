document.addEventListener("keypress", function (e) {
    let color = '';
    if (e.key.toLowerCase() === 'w') {
        color = 'white';
    } else if (e.key.toLowerCase() === 'y') {
        color = 'yellow';
    } else if (e.key.toLowerCase() === 'r') {
        color = 'red';
    }
    document.getElementById("tests").className = '';
    document.getElementById("tests").classList.add(color);
});
