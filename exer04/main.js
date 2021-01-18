const player = document.getElementById("reproductor");
player.addEventListener('pause', () => {
    alert('Music is off!');
});

player.addEventListener('play', () => {
    alert('Music is on!');
});

