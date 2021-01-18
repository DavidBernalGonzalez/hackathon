lastId = 0;
element = '';

setInterval(() => {
    removeClass("target");
    const id = Math.floor(Math.random() * 9) + 1;
    this.lastId = id;
    const element = document.getElementById(id);
    element.classList.add("target");
    element.addEventListener("click", printMsg);
    this.element = element;
}, 3000);

function removeClass(className) {
    try {
        if (document.getElementsByClassName(className)) {
            const faqToggle = document.getElementsByClassName(className)[0];
            if (faqToggle.classList.contains(className)) {
                faqToggle.classList.remove(className);
            }
            element.removeEventListener("click", printMsg);
        }
    } catch (err) { }
}

const printMsg = () => alert("Has ganado");

