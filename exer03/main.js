const form = document.getElementById("contacte");
// form.addEventListener('submit', myScript);
form.addEventListener('submit', event => {
    checkFields = false;
    checkField("name");
    checkField("mail");
    checkField("message");

    if(checkField("name") && checkField("mail") && checkField("message")){
        alert('Form submission success.');
    } else{
        event.preventDefault();
        alert('Form submission cancelled.');
    }
});

const checkField = (field) => {
    let check = false;
    if (document.getElementById(field).value === '') {
        this.check = false;
    } else {
        this.check = true;
    }
    changeColor(field);
    return this.check;
}

function changeColor(field) {
    if (document.getElementById(field).value === '') {
        document.getElementById(field).className = '';
        document.getElementById(field).classList.add("error");
    } else {
        document.getElementById(field).className = '';
        document.getElementById(field).classList.add("correct");
    }
}


