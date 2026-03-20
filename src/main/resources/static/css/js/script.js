const BASE_URL = "http://localhost:8080";

// 🔐 AUTO REDIRECT
document.addEventListener("DOMContentLoaded", () => {
    const path = window.location.pathname;

    if ((path.includes("upload.html")) && !localStorage.getItem("loggedIn")) {
        window.location.href = "login.html";
    }

    const savedLang = localStorage.getItem("language") || "en";
    changeLanguage(savedLang);

    const username = localStorage.getItem("username") || "User";
    const userWelcome = document.getElementById("uploadUserWelcome");
    if (userWelcome) userWelcome.textContent = `Welcome, ${username}`;

    setupPlantAutocomplete();
});

// 🌐 TRANSLATIONS
const translations = {
    en: {
        title: "🌿 Plant Leaf Disease Analyzer",
        uploadTitle: "Upload Leaf Image for Analysis",
        plantPlaceholder: "Click here to select plant...",
        customPlaceholder: "Enter any plant name (e.g. Mango, Orange, etc.)",
        analyzeBtn: "🔬 Analyze Image",
        analyzing: "🔄 Analyzing image... Please wait",
        resultTitle: "✅ Analysis Complete!",
        plantLabel: "Plant",
        diseaseLabel: "Disease",
        confidenceLabel: "Confidence",
        logoutBtn: "Logout",
        customTitle: "🌿 Custom Plant Name"
    },
    hi: {
        title: "🌿 पौधे के पत्ते रोग विश्लेषक",
        uploadTitle: "पत्ती छवि अपलोड करें",
        plantPlaceholder: "पौधा चुनें...",
        customPlaceholder: "कोई भी पौधे का नाम डालें",
        analyzeBtn: "🔬 छवि विश्लेषण करें",
        analyzing: "🔄 विश्लेषण हो रहा है",
        resultTitle: "✅ विश्लेषण पूर्ण!",
        plantLabel: "पौधा",
        diseaseLabel: "रोग",
        confidenceLabel: "विश्वास स्तर",
        logoutBtn: "लॉग आउट",
        customTitle: "🌿 कस्टम पौधे का नाम"
    },
    ta: {
        title: "🌿 இலை நோய் பரிசோதகர்",
        uploadTitle: "இலை படத்தை பதிவேற்றவும்",
        plantPlaceholder: "தாவரத்தை தேர்வு செய்க",
        customPlaceholder: "தாவர பெயர் உள்ளிடவும்",
        analyzeBtn: "🔬 பகுப்பாய்வு செய்",
        analyzing: "🔄 பகுப்பாய்வு நடைபெறுகிறது",
        resultTitle: "✅ முடிந்தது!",
        plantLabel: "தாவரம்",
        diseaseLabel: "நோய்",
        confidenceLabel: "நம்பகத்தன்மை",
        logoutBtn: "லாக்அவுட்",
        customTitle: "🌿 தனிப்பயன் தாவர பெயர்"
    }
};

function changeLanguage(lang) {
    localStorage.setItem("language", lang);

    document.querySelectorAll("[data-translate]").forEach(el => {
        const key = el.dataset.translate;
        if (translations[lang][key]) el.textContent = translations[lang][key];
    });

    document.querySelectorAll("[data-translate-placeholder]").forEach(el => {
        const key = el.dataset.translatePlaceholder;
        if (translations[lang][key]) el.placeholder = translations[lang][key];
    });
}

// 🔐 LOGIN
async function login() {
    const user = document.getElementById("username").value.trim();
    const pass = document.getElementById("password").value.trim();

    if (!user || !pass) {
        alert("Enter username and password");
        return;
    }

    try {
        const res = await fetch(`${BASE_URL}/api/auth/login`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({username: user, password: pass})
        });

        const data = await res.json();

        if (data.success) {
            localStorage.setItem("loggedIn", "true");
            localStorage.setItem("username", user);
            window.location.href = "upload.html";
        } else {
            alert("Invalid username or password");
        }

    } catch (e) {
        alert("Server error");
        console.error(e);
    }
}

// 🔓 LOGOUT
function logout() {
    localStorage.clear();
    window.location.href = "login.html";
}

// 🌱 PLANT AUTOCOMPLETE
const plantNames = ["🌿Tomato", "🌿Potato", "🌿Apple", "🌿Grape", "🌿Strawberry", "Other"];

function setupPlantAutocomplete() {
    const plantInput = document.getElementById("plantName");
    const dropdown = document.getElementById("plantDropdown");

    if (!plantInput || !dropdown) return;

    // Show all plants when input is clicked
    plantInput.addEventListener("focus", showAllPlants);

    // Filter plants when typing
    plantInput.addEventListener("input", filterPlants);

    // Hide dropdown when clicking outside
    document.addEventListener("click", (e) => {
        if (!plantInput.contains(e.target) && !dropdown.contains(e.target)) {
            dropdown.style.display = "none";
        }
    });

    function showAllPlants() {
        dropdown.innerHTML = "";

        plantNames.forEach(p => {
            const div = document.createElement("div");
            div.className = "autocomplete-item";
            div.textContent = p;

            div.onclick = () => {
                selectPlant(p);
                dropdown.style.display = "none"; // ✅ hide menu after selection
            };

            dropdown.appendChild(div);
        });

        dropdown.style.display = "block";
    }

    function filterPlants() {
        const val = plantInput.value.toLowerCase();
        dropdown.innerHTML = "";

        plantNames.forEach(p => {
            if (p.toLowerCase().includes(val)) {
                const div = document.createElement("div");
                div.className = "autocomplete-item";
                div.textContent = p;

                div.onclick = () => {
                    selectPlant(p);
                    dropdown.style.display = "none"; // ✅ hide menu
                };

                dropdown.appendChild(div);
            }
        });

        dropdown.style.display = "block";
    }
}

function selectPlant(p) {
    const plantInput = document.getElementById("plantName");
    const customDiv = document.getElementById("otherPlantInput");

    if (p === "Other") {
        plantInput.value = "";
        customDiv.classList.add("active"); // ✅ show custom input box
    } else {
        plantInput.value = p;
        customDiv.classList.remove("active"); // ✅ hide custom input box
    }
}



// 🖼 IMAGE PREVIEW
function previewImage() {
    const file = document.getElementById("image").files[0];
    const preview = document.getElementById("preview");
    preview.src = URL.createObjectURL(file);
    preview.style.display = "block";
}

// 🚀 UPLOAD IMAGE
function uploadImage() {
    let plantName = document.getElementById("plantName").value.trim();
    const customName = document.getElementById("customPlantName").value.trim();
    const image = document.getElementById("image").files[0];

    if (!plantName && customName) plantName = customName;
    if (!plantName || !image) {
        alert("Select plant and image");
        return;
    }

    const loading = document.getElementById("loading");
    loading.style.display = "block";

    const formData = new FormData();
    formData.append("plantName", plantName);
    formData.append("image", image);

    fetch(`${BASE_URL}/api/analyze`, {
        method: "POST",
        body: formData
    })
    .then(res => res.json())
    .then(data => {
        loading.style.display = "none";
        document.getElementById("result").style.display = "block";
        document.getElementById("resPlant").textContent = data.plant;
        document.getElementById("resDisease").textContent = data.disease;
        document.getElementById("resConfidence").textContent = data.confidence ;
    })
    .catch(err => {
        loading.style.display = "none";
        alert("Server error");
        console.error(err);
    });
}
