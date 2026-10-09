/**
 * PawNest - Online Pet Adoption Platform
 * Complete Frontend Controller & State Management
 * Supports Admin, Shelter, and Adopter roles with persistent state,
 * interactive demo walkthroughs, favorites, two-way chat, and celebration effects.
 */

// ==========================================
// 1. DEFAULT INITIAL MOCK DATA
// ==========================================
const DEFAULT_PETS = [
  {
    id: "pet-101",
    name: "Barnaby",
    type: "Dog",
    breed: "Golden Retriever Mix",
    age: "2 years",
    gender: "Male",
    location: "Seattle, WA",
    shelterId: "sh-1",
    shelterName: "Happy Paws Rescue & Shelter",
    image: "https://images.unsplash.com/photo-1552053831-71594a27632d?auto=format&fit=crop&w=700&q=80",
    description: "Barnaby is an outgoing, tennis-ball-obsessed companion who loves gentle cuddles, outdoor trails, and learning new agility tricks. Neutered, microchipped, and heartworm negative.",
    vaccinations: "Fully Up to Date",
    goodWith: "Kids, Cats & Dogs",
    approvalStatus: "Approved",
    adoptionStatus: "Available",
    submittedDate: "2026-09-15"
  },
  {
    id: "pet-102",
    name: "Luna",
    type: "Cat",
    breed: "Russian Blue",
    age: "1.5 years",
    gender: "Female",
    location: "Seattle, WA",
    shelterId: "sh-1",
    shelterName: "Happy Paws Rescue & Shelter",
    image: "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=700&q=80",
    description: "Luna is a calm, purring beauty who loves sunny windowsills, feather wands, and quiet companions. Spayed and litter-box trained.",
    vaccinations: "Fully Up to Date",
    goodWith: "Gentle Kids & Cats",
    approvalStatus: "Approved",
    adoptionStatus: "Available",
    submittedDate: "2026-09-18"
  },
  {
    id: "pet-103",
    name: "Oliver",
    type: "Rabbit",
    breed: "Holland Lop",
    age: "8 months",
    gender: "Male",
    location: "Portland, OR",
    shelterId: "sh-2",
    shelterName: "Cascade Animal Haven",
    image: "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=700&q=80",
    description: "Oliver loves fresh timothy hay, cardboard tunnels, and getting nose boops. Super tidy and quiet companion for an apartment.",
    vaccinations: "Health Checked",
    goodWith: "Older Children & Calm Pets",
    approvalStatus: "Approved",
    adoptionStatus: "Available",
    submittedDate: "2026-09-22"
  },
  {
    id: "pet-104",
    name: "Milo & Kiwi",
    type: "Bird",
    breed: "Parakeets / Budgies",
    age: "1 year",
    gender: "Male",
    location: "Seattle, WA",
    shelterId: "sh-1",
    shelterName: "Happy Paws Rescue & Shelter",
    image: "https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=700&q=80",
    description: "Bonded pair of cheerful singing budgies. They love fresh veggies, bells, and hopping together. Must be adopted as a pair.",
    vaccinations: "Avian Vet Inspected",
    goodWith: "All households",
    approvalStatus: "Approved",
    adoptionStatus: "Available",
    submittedDate: "2026-09-24"
  },
  {
    id: "pet-105",
    name: "Milo",
    type: "Dog",
    breed: "Australian Shepherd",
    age: "3 years",
    gender: "Male",
    location: "San Francisco, CA",
    shelterId: "sh-3",
    shelterName: "Bay Area Paws & Tails",
    image: "https://images.unsplash.com/photo-1503256207526-0d5d80fa2f47?auto=format&fit=crop&w=700&q=80",
    description: "Milo is a vibrant, intelligent herding dog who thrives with mental puzzles, frisbee, and trail running. Ideal for an active lifestyle.",
    vaccinations: "Fully Up to Date",
    goodWith: "Active families & dogs",
    approvalStatus: "Approved",
    adoptionStatus: "Pending",
    submittedDate: "2026-09-10"
  },
  {
    id: "pet-106",
    name: "Bella",
    type: "Cat",
    breed: "Calico Domestic Shorthair",
    age: "4 years",
    gender: "Female",
    location: "Portland, OR",
    shelterId: "sh-2",
    shelterName: "Cascade Animal Haven",
    image: "https://images.unsplash.com/photo-1533738363-b7f9aef128ce?auto=format&fit=crop&w=700&q=80",
    description: "Bella is a gentle lap cat with gorgeous tricolor patches. Enjoys chin scratches, nap times, and quiet evenings watching birds.",
    vaccinations: "Up to Date",
    goodWith: "Families & single parents",
    approvalStatus: "Approved",
    adoptionStatus: "Available",
    submittedDate: "2026-09-25"
  },
  {
    id: "pet-107",
    name: "Ranger",
    type: "Dog",
    breed: "German Shepherd Puppy",
    age: "4 months",
    gender: "Male",
    location: "Seattle, WA",
    shelterId: "sh-1",
    shelterName: "Happy Paws Rescue & Shelter",
    image: "https://images.unsplash.com/photo-1589941013453-ec89f33b5e95?auto=format&fit=crop&w=700&q=80",
    description: "Playful pup rescued with his litter. In training, eager to learn, and full of sweet puppy energy. Awaiting Admin Approval before going public.",
    vaccinations: "Puppy series started",
    goodWith: "Kids & dogs with training",
    approvalStatus: "Pending",
    adoptionStatus: "Available",
    submittedDate: "2026-09-29"
  }
];

const DEFAULT_USERS = [
  { id: "usr-1", name: "Admin Officer Chen", email: "admin@pawnest.org", role: "Admin", status: "Active", createdDate: "2026-01-10" },
  { id: "usr-2", name: "Happy Paws Shelter Staff", email: "contact@happypaws.org", role: "Shelter", status: "Active", createdDate: "2026-02-14" },
  { id: "usr-3", name: "Cascade Animal Haven", email: "info@cascadeanimals.org", role: "Shelter", status: "Active", createdDate: "2026-03-01" },
  { id: "usr-4", name: "Sarah Jenkins", email: "sarah.jenkins@example.com", role: "Adopter", status: "Active", createdDate: "2026-05-18" },
  { id: "usr-5", name: "David Martinez", email: "david.m@example.com", role: "Adopter", status: "Active", createdDate: "2026-06-20" },
  { id: "usr-6", name: "Emily Watson", email: "emily.w@example.com", role: "Adopter", status: "Pending", createdDate: "2026-09-28" }
];

const DEFAULT_APPLICATIONS = [
  {
    id: "APP-101",
    petId: "pet-101",
    petName: "Barnaby",
    petBreed: "Golden Retriever Mix",
    applicantName: "Sarah Jenkins",
    applicantEmail: "sarah.jenkins@example.com",
    shelterId: "sh-1",
    housingType: "House with Fenced Yard",
    otherPets: "No other pets",
    reason: "Looking for an affectionate hiking and jogging buddy. Work from home full-time.",
    status: "Under Review",
    submittedDate: "2026-09-26",
    timeline: [
      { step: "Submitted", date: "Sep 26, 2026", status: "done" },
      { step: "Shelter Review", date: "Sep 27, 2026", status: "current" },
      { step: "Home Check / Interview", date: "Pending", status: "upcoming" },
      { step: "Final Handover", date: "Pending", status: "upcoming" }
    ]
  },
  {
    id: "APP-102",
    petId: "pet-102",
    petName: "Luna",
    petBreed: "Russian Blue",
    applicantName: "Sarah Jenkins",
    applicantEmail: "sarah.jenkins@example.com",
    shelterId: "sh-1",
    housingType: "Apartment / Condo",
    otherPets: "No other pets",
    reason: "A quiet, gentle companion to share cozy evenings.",
    status: "Pending",
    submittedDate: "2026-09-28",
    timeline: [
      { step: "Submitted", date: "Sep 28, 2026", status: "done" },
      { step: "Shelter Review", date: "Pending", status: "current" },
      { step: "Home Check / Interview", date: "Pending", status: "upcoming" },
      { step: "Final Handover", date: "Pending", status: "upcoming" }
    ]
  },
  {
    id: "APP-103",
    petId: "pet-105",
    petName: "Milo",
    petBreed: "Australian Shepherd",
    applicantName: "David Martinez",
    applicantEmail: "david.m@example.com",
    shelterId: "sh-3",
    housingType: "House with Fenced Yard",
    otherPets: "1 Dog",
    reason: "Experienced active dog owner seeking companion for agility training.",
    status: "Approved",
    submittedDate: "2026-09-20",
    timeline: [
      { step: "Submitted", date: "Sep 20, 2026", status: "done" },
      { step: "Shelter Review", date: "Sep 21, 2026", status: "done" },
      { step: "Home Check / Interview", date: "Sep 24, 2026", status: "done" },
      { step: "Final Handover", date: "In Progress", status: "current" }
    ]
  }
];

const DEFAULT_HISTORY = [
  {
    id: "HIST-098",
    petName: "Buster",
    breed: "Corgi Mix",
    type: "Dog",
    shelterName: "Cascade Animal Haven",
    adoptedDate: "May 14, 2025",
    image: "https://images.unsplash.com/photo-1612536057832-2ff7ead58194?auto=format&fit=crop&w=600&q=80",
    story: "Buster has brought immense joy, love, and daily giggles to our family for over a year now!"
  }
];

const DEFAULT_MESSAGES = [
  {
    id: "msg-1",
    sender: "adopter",
    applicantName: "Sarah Jenkins",
    text: "Hello! I submitted an application for Barnaby. Does he do well around cats during neighborhood walks?",
    time: "Yesterday, 3:15 PM"
  },
  {
    id: "msg-2",
    sender: "shelter",
    applicantName: "Sarah Jenkins",
    text: "Hi Sarah! Barnaby has shown very low prey drive in our test play yards and mostly ignores other animals politely. We would love to arrange a meet-and-greet this Saturday!",
    time: "Yesterday, 4:40 PM"
  }
];

// ==========================================
// 2. STATE STORAGE INITIALIZATION
// ==========================================
let state = {
  currentRole: "adopter",
  pets: JSON.parse(localStorage.getItem("pawnest_pets")) || DEFAULT_PETS,
  users: JSON.parse(localStorage.getItem("pawnest_users")) || DEFAULT_USERS,
  applications: JSON.parse(localStorage.getItem("pawnest_apps")) || DEFAULT_APPLICATIONS,
  history: JSON.parse(localStorage.getItem("pawnest_hist")) || DEFAULT_HISTORY,
  messages: JSON.parse(localStorage.getItem("pawnest_msgs")) || DEFAULT_MESSAGES,
  favorites: JSON.parse(localStorage.getItem("pawnest_favs")) || ["pet-101"],
  showFavoritesOnly: false,
  adopterProfile: JSON.parse(localStorage.getItem("pawnest_profile")) || {
    name: "Sarah Jenkins",
    email: "sarah.jenkins@example.com",
    phone: "+1 (206) 555-0192",
    city: "Seattle, WA",
    address: "742 Evergreen Terrace, Apt 4B",
    residence: "House with Fenced Yard",
    experience: "Experienced Pet Parent (5+ years)",
    bio: "Work from home, active lifestyle, love weekend hikes. Looking for an energetic or affectionate companion."
  },
  settings: JSON.parse(localStorage.getItem("pawnest_settings")) || {
    platformName: "PawNest Online Pet Adoption",
    supportEmail: "support@pawnest.org",
    adoptionFee: 25,
    maxListings: 50,
    autoArchiveDays: 45,
    requireApproval: true,
    emailAlerts: true
  }
};

function saveState() {
  localStorage.setItem("pawnest_pets", JSON.stringify(state.pets));
  localStorage.setItem("pawnest_users", JSON.stringify(state.users));
  localStorage.setItem("pawnest_apps", JSON.stringify(state.applications));
  localStorage.setItem("pawnest_hist", JSON.stringify(state.history));
  localStorage.setItem("pawnest_msgs", JSON.stringify(state.messages));
  localStorage.setItem("pawnest_favs", JSON.stringify(state.favorites));
  localStorage.setItem("pawnest_profile", JSON.stringify(state.adopterProfile));
  localStorage.setItem("pawnest_settings", JSON.stringify(state.settings));
}

// ==========================================
// 3. UI NOTIFICATIONS, CONFETTI & HELPERS
// ==========================================
function triggerConfetti() {
  if (typeof confetti === "function") {
    confetti({
      particleCount: 80,
      spread: 70,
      origin: { y: 0.6 }
    });
  }
}

function showToast(message, type = "success") {
  const container = document.getElementById("toast-container");
  if (!container) return;

  const toast = document.createElement("div");
  toast.className = `toast px-4 py-3 rounded-xl border flex items-center gap-3 text-xs font-semibold animate-slide-down ${
    type === "success" ? "bg-emerald-50 text-emerald-800 border-emerald-200" :
    type === "error" ? "bg-rose-50 text-rose-800 border-rose-200" :
    "bg-sky-50 text-sky-800 border-sky-200"
  }`;

  const iconName = type === "success" ? "check-circle" : type === "error" ? "alert-circle" : "info";
  toast.innerHTML = `
    <i data-lucide="${iconName}" class="w-4 h-4 shrink-0 ${type === "success" ? "text-emerald-600" : type === "error" ? "text-rose-600" : "text-sky-600"}"></i>
    <span class="flex-1">${message}</span>
    <button onclick="this.parentElement.remove()" class="text-slate-400 hover:text-slate-600 ml-2">
      <i data-lucide="x" class="w-3.5 h-3.5"></i>
    </button>
  `;

  container.appendChild(toast);
  lucide.createIcons();

  setTimeout(() => {
    toast.style.opacity = "0";
    toast.style.transform = "translateY(10px)";
    setTimeout(() => toast.remove(), 300);
  }, 4000);
}

function copyAppId(id, event) {
  if (event) event.stopPropagation();
  navigator.clipboard.writeText(id).then(() => {
    showToast(`Copied Application ID "${id}" to clipboard!`, "info");
  }).catch(() => {
    showToast(`Application ID: ${id}`, "info");
  });
}

// ==========================================
// 4. ROLE SWITCHING CONTROLLER
// ==========================================
function switchRole(newRole) {
  state.currentRole = newRole;

  document.querySelectorAll(".role-switch-btn").forEach(btn => {
    btn.className = "role-switch-btn flex items-center gap-1.5 px-3 py-1.5 text-xs font-semibold rounded-lg transition-all duration-200 text-slate-600 hover:text-slate-900";
  });

  const activeBtn = document.getElementById(`role-btn-${newRole}`);
  if (activeBtn) {
    if (newRole === "adopter") {
      activeBtn.className = "role-switch-btn flex items-center gap-1.5 px-3 py-1.5 text-xs font-semibold rounded-lg transition-all duration-200 bg-white text-orange-600 shadow-sm";
    } else if (newRole === "shelter") {
      activeBtn.className = "role-switch-btn flex items-center gap-1.5 px-3 py-1.5 text-xs font-semibold rounded-lg transition-all duration-200 bg-white text-teal-700 shadow-sm";
    } else if (newRole === "admin") {
      activeBtn.className = "role-switch-btn flex items-center gap-1.5 px-3 py-1.5 text-xs font-semibold rounded-lg transition-all duration-200 bg-white text-purple-700 shadow-sm";
    }
  }

  document.querySelectorAll(".dashboard-view").forEach(view => view.classList.add("hidden"));
  const targetView = document.getElementById(`view-${newRole}`);
  if (targetView) targetView.classList.remove("hidden");

  const avatar = document.getElementById("currentUserAvatar");
  const nameEl = document.getElementById("currentUserName");
  const roleEl = document.getElementById("currentUserRole");
  const bannerText = document.getElementById("role-banner-text");

  if (newRole === "adopter") {
    avatar.className = "w-8 h-8 rounded-full bg-orange-500 text-white flex items-center justify-center font-bold text-xs ring-2 ring-orange-200";
    avatar.innerText = "SJ";
    nameEl.innerText = state.adopterProfile.name;
    roleEl.innerText = "Adopter Persona";
    roleEl.className = "text-[10px] text-orange-600 font-medium capitalize";
    bannerText.innerHTML = "Viewing as <strong>Adopter</strong>. Browse available pets, apply for adoption, and track status.";
    renderAdopterPets();
    renderAdopterApplications();
    renderAdopterHistory();
    renderAdopterChat();
  } else if (newRole === "shelter") {
    avatar.className = "w-8 h-8 rounded-full bg-teal-600 text-white flex items-center justify-center font-bold text-xs ring-2 ring-teal-200";
    avatar.innerText = "HP";
    nameEl.innerText = "Happy Paws Shelter";
    roleEl.innerText = "Shelter Partner";
    roleEl.className = "text-[10px] text-teal-600 font-medium capitalize";
    bannerText.innerHTML = "Viewing as <strong>Shelter</strong>. Manage pet listings, review adoption applications, and message adopters.";
    renderShelterListings();
    renderShelterApplications();
    renderShelterChat();
    updateShelterStats();
  } else if (newRole === "admin") {
    avatar.className = "w-8 h-8 rounded-full bg-purple-600 text-white flex items-center justify-center font-bold text-xs ring-2 ring-purple-200";
    avatar.innerText = "AD";
    nameEl.innerText = "System Administrator";
    roleEl.innerText = "Admin Console";
    roleEl.className = "text-[10px] text-purple-600 font-medium capitalize";
    bannerText.innerHTML = "Viewing as <strong>Administrator</strong>. Manage platform users, approve/reject pet listings, and configure settings.";
    renderAdminUsers();
    renderAdminApprovals();
    renderAdminAnalytics();
  }

  lucide.createIcons();
}

function resetDataConfirmation() {
  if (confirm("Reset all platform data back to initial demo state?")) {
    localStorage.clear();
    state.pets = JSON.parse(JSON.stringify(DEFAULT_PETS));
    state.users = JSON.parse(JSON.stringify(DEFAULT_USERS));
    state.applications = JSON.parse(JSON.stringify(DEFAULT_APPLICATIONS));
    state.history = JSON.parse(JSON.stringify(DEFAULT_HISTORY));
    state.messages = JSON.parse(JSON.stringify(DEFAULT_MESSAGES));
    state.favorites = ["pet-101"];
    saveState();
    showToast("Platform demo data has been reset to defaults!", "info");
    switchRole(state.currentRole);
  }
}

// ==========================================
// 5. ADOPTER CONTROLLER & VIEWS
// ==========================================
function switchAdopterTab(tabName) {
  const tabs = ["browse", "applications", "messages", "history", "profile"];
  tabs.forEach(t => {
    const el = document.getElementById(`adopter-subtab-${t}`);
    const btn = document.getElementById(`adopter-tab-${t}`);
    if (el) el.classList.toggle("hidden", t !== tabName);
    if (btn) {
      if (t === tabName) {
        btn.className = "adopter-nav-btn px-4 py-2 text-sm font-semibold rounded-lg bg-orange-600 text-white shadow-sm flex items-center gap-2";
      } else {
        btn.className = "adopter-nav-btn px-4 py-2 text-sm font-semibold rounded-lg text-slate-600 hover:bg-slate-100 flex items-center gap-2";
      }
    }
  });

  if (tabName === "browse") renderAdopterPets();
  if (tabName === "applications") renderAdopterApplications();
  if (tabName === "messages") renderAdopterChat();
  if (tabName === "history") renderAdopterHistory();
  if (tabName === "profile") loadAdopterProfile();
  lucide.createIcons();
}

function toggleFavorite(petId, event) {
  if (event) event.stopPropagation();
  const idx = state.favorites.indexOf(petId);
  if (idx > -1) {
    state.favorites.splice(idx, 1);
    showToast("Removed pet from your saved favorites.", "info");
  } else {
    state.favorites.push(petId);
    showToast("Added pet to your saved favorites! ❤️", "success");
  }
  saveState();
  updateFavoritesBadge();
  renderAdopterPets();
}

function toggleFavoritesOnlyFilter() {
  state.showFavoritesOnly = !state.showFavoritesOnly;
  const btn = document.getElementById("favorites-filter-btn");
  if (btn) {
    if (state.showFavoritesOnly) {
      btn.className = "px-3 py-1 rounded-full text-xs font-semibold border border-rose-500 bg-rose-50 text-rose-700 flex items-center gap-1.5 transition-colors";
    } else {
      btn.className = "px-3 py-1 rounded-full text-xs font-semibold border border-slate-200 bg-white text-slate-700 hover:text-rose-600 flex items-center gap-1.5 transition-colors";
    }
  }
  filterPets();
}

function updateFavoritesBadge() {
  const badge = document.getElementById("favorites-count");
  if (badge) badge.innerText = state.favorites.length;
}

let activePetTypeFilter = "ALL";

function setPetTypeFilter(type) {
  activePetTypeFilter = type;
  document.getElementById("petTypeFilter").value = type;
  document.querySelectorAll(".pet-quick-pill").forEach(pill => {
    if (pill.dataset.filter === type) {
      pill.className = "pet-quick-pill px-3 py-1 rounded-full text-xs font-semibold border bg-orange-600 text-white border-orange-600";
    } else {
      pill.className = "pet-quick-pill px-3 py-1 rounded-full text-xs font-semibold border bg-white text-slate-700 border-slate-200 hover:border-orange-300";
    }
  });
  filterPets();
}

function filterPets() {
  const search = (document.getElementById("petSearchInput")?.value || "").toLowerCase();
  const typeFilter = document.getElementById("petTypeFilter")?.value || "ALL";
  const locFilter = document.getElementById("petLocationFilter")?.value || "ALL";

  const approvedPets = state.pets.filter(p => p.approvalStatus === "Approved");

  const filtered = approvedPets.filter(pet => {
    const matchesSearch = !search || 
      pet.name.toLowerCase().includes(search) || 
      pet.breed.toLowerCase().includes(search) || 
      pet.description.toLowerCase().includes(search);
    
    const matchesType = typeFilter === "ALL" || pet.type === typeFilter;
    const matchesLoc = locFilter === "ALL" || pet.location === locFilter;
    const matchesFav = !state.showFavoritesOnly || state.favorites.includes(pet.id);

    return matchesSearch && matchesType && matchesLoc && matchesFav;
  });

  const countEl = document.getElementById("petResultsCount");
  if (countEl) countEl.innerText = filtered.length;
  renderPetsGrid(filtered);
}

function renderAdopterPets() {
  updateFavoritesBadge();
  filterPets();
}

function renderPetsGrid(pets) {
  const container = document.getElementById("petsGrid");
  if (!container) return;

  if (pets.length === 0) {
    container.innerHTML = `
      <div class="col-span-full py-16 text-center bg-white rounded-2xl border border-dashed border-slate-200">
        <i data-lucide="paw-print" class="w-12 h-12 text-slate-300 mx-auto mb-2"></i>
        <h3 class="text-sm font-bold text-slate-700">No pets found matching criteria</h3>
        <p class="text-xs text-slate-400 mt-1">Try clearing filters or search terms.</p>
        <button onclick="clearPetFilters()" class="mt-4 px-3 py-1.5 text-xs bg-orange-100 text-orange-700 font-semibold rounded-lg hover:bg-orange-200">Reset Filters</button>
      </div>
    `;
    lucide.createIcons();
    return;
  }

  container.innerHTML = pets.map(pet => {
    const isFav = state.favorites.includes(pet.id);
    return `
      <div class="bg-white rounded-2xl border border-slate-200 overflow-hidden shadow-sm hover:shadow-md transition-shadow group flex flex-col relative">
        <div class="relative h-48 overflow-hidden bg-slate-100">
          <img src="${pet.image}" alt="${pet.name}" class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300">
          
          <!-- Status tag -->
          <span class="absolute top-3 left-3 px-2.5 py-0.5 rounded-full text-[11px] font-bold shadow-sm ${
            pet.adoptionStatus === 'Available' ? 'bg-emerald-500 text-white' : 'bg-amber-500 text-white'
          }">
            ${pet.adoptionStatus}
          </span>

          <!-- Favorite Button -->
          <button onclick="toggleFavorite('${pet.id}', event)" class="absolute top-3 right-3 w-8 h-8 rounded-full bg-white/90 backdrop-blur flex items-center justify-center shadow hover:scale-110 transition-transform">
            <i data-lucide="heart" class="w-4 h-4 ${isFav ? 'text-rose-500 fill-rose-500' : 'text-slate-400'}"></i>
          </button>
        </div>

        <div class="p-5 flex-1 flex flex-col justify-between">
          <div>
            <div class="flex items-baseline justify-between mb-1">
              <h3 class="text-lg font-bold text-slate-900 group-hover:text-orange-600 transition-colors">${pet.name}</h3>
              <span class="text-xs text-slate-500 font-medium">${pet.age}</span>
            </div>

            <div class="text-xs text-slate-500 mb-2 flex items-center gap-1 font-medium">
              <i data-lucide="info" class="w-3.5 h-3.5 text-orange-500"></i>
              ${pet.breed} &bull; ${pet.gender}
            </div>

            <p class="text-xs text-slate-600 line-clamp-2 mb-3 leading-relaxed">
              ${pet.description}
            </p>

            <div class="flex items-center gap-1 text-[11px] text-slate-400 mb-4">
              <i data-lucide="map-pin" class="w-3 h-3 text-slate-400"></i>
              ${pet.location} &bull; <span class="text-teal-600 font-medium">${pet.shelterName}</span>
            </div>
          </div>

          <button onclick="openPetDetailModal('${pet.id}')" class="w-full py-2.5 px-4 bg-orange-500 hover:bg-orange-600 text-white text-xs font-bold rounded-xl shadow-sm transition-colors flex items-center justify-center gap-2">
            <span>View Details & Apply</span>
            <i data-lucide="arrow-right" class="w-3.5 h-3.5"></i>
          </button>
        </div>
      </div>
    `;
  }).join("");

  lucide.createIcons();
}

function clearPetFilters() {
  if (document.getElementById("petSearchInput")) document.getElementById("petSearchInput").value = "";
  if (document.getElementById("petTypeFilter")) document.getElementById("petTypeFilter").value = "ALL";
  if (document.getElementById("petLocationFilter")) document.getElementById("petLocationFilter").value = "ALL";
  state.showFavoritesOnly = false;
  const btn = document.getElementById("favorites-filter-btn");
  if (btn) btn.className = "px-3 py-1 rounded-full text-xs font-semibold border border-slate-200 bg-white text-slate-700 hover:text-rose-600 flex items-center gap-1.5 transition-colors";
  setPetTypeFilter("ALL");
}

function openPetDetailModal(petId) {
  const pet = state.pets.find(p => p.id === petId);
  if (!pet) return;

  document.getElementById("appPetId").value = pet.id;
  document.getElementById("modalPetImage").src = pet.image;
  document.getElementById("modalPetName").innerText = pet.name;
  document.getElementById("modalPetBreed").innerText = `${pet.breed} • ${pet.age} • ${pet.gender}`;
  document.getElementById("modalPetShelter").innerText = pet.shelterName;
  document.getElementById("modalPetLocation").innerText = pet.location;
  document.getElementById("modalPetVaccines").innerText = pet.vaccinations;
  document.getElementById("modalPetGoodWith").innerText = pet.goodWith;
  document.getElementById("modalPetDescription").innerText = pet.description;
  document.getElementById("modalPetStatusBadge").innerText = pet.adoptionStatus;

  document.getElementById("appApplicantName").value = state.adopterProfile.name;
  document.getElementById("appApplicantEmail").value = state.adopterProfile.email;
  document.getElementById("appHousingType").value = state.adopterProfile.residence || "House with Fenced Yard";
  document.getElementById("appReason").value = "";

  document.getElementById("petDetailModal").classList.remove("hidden");
  lucide.createIcons();
}

function closePetDetailModal() {
  document.getElementById("petDetailModal").classList.add("hidden");
}

function autoFillSampleApplication() {
  document.getElementById("appApplicantName").value = state.adopterProfile.name;
  document.getElementById("appApplicantEmail").value = state.adopterProfile.email;
  document.getElementById("appHousingType").value = "House with Fenced Yard";
  document.getElementById("appOtherPets").value = "No other pets";
  document.getElementById("appReason").value = "I work from home, have a fully fenced backyard, and walk 5km daily. Looking for an energetic lifelong buddy!";
  showToast("Application pre-filled with sample adopter data!", "info");
}

function submitAdoptionApplication(event) {
  event.preventDefault();
  const petId = document.getElementById("appPetId").value;
  const pet = state.pets.find(p => p.id === petId);
  if (!pet) return;

  const applicantName = document.getElementById("appApplicantName").value.trim();
  const applicantEmail = document.getElementById("appApplicantEmail").value.trim();
  const housingType = document.getElementById("appHousingType").value;
  const otherPets = document.getElementById("appOtherPets").value;
  const reason = document.getElementById("appReason").value.trim();

  const newAppId = "APP-" + Math.floor(100 + Math.random() * 900);

  const newApp = {
    id: newAppId,
    petId: pet.id,
    petName: pet.name,
    petBreed: pet.breed,
    applicantName,
    applicantEmail,
    shelterId: pet.shelterId,
    housingType,
    otherPets,
    reason,
    status: "Pending",
    submittedDate: new Date().toISOString().split("T")[0],
    timeline: [
      { step: "Submitted", date: "Just now", status: "done" },
      { step: "Shelter Review", date: "Pending", status: "current" },
      { step: "Home Check / Interview", date: "Pending", status: "upcoming" },
      { step: "Final Handover", date: "Pending", status: "upcoming" }
    ]
  };

  state.applications.unshift(newApp);

  state.messages.push({
    id: "msg-" + Date.now(),
    sender: "adopter",
    applicantName,
    text: `Application ${newAppId} submitted for ${pet.name}. Looking forward to discussing next steps!`,
    time: "Just now"
  });

  saveState();
  closePetDetailModal();
  triggerConfetti();

  showToast(`Application ${newAppId} submitted successfully for ${pet.name}!`, "success");
  switchAdopterTab("applications");
}

// Render Adopter Applications with 1-click status advances & copy ID
function renderAdopterApplications() {
  const container = document.getElementById("adopterApplicationsList");
  const countBadge = document.getElementById("adopter-app-count-badge");
  if (!container) return;

  const myApps = state.applications.filter(a => a.applicantEmail.toLowerCase() === state.adopterProfile.email.toLowerCase());
  if (countBadge) countBadge.innerText = myApps.length;

  if (myApps.length === 0) {
    container.innerHTML = `
      <div class="p-8 text-center bg-slate-50 rounded-xl border border-dashed border-slate-200">
        <i data-lucide="file-question" class="w-10 h-10 text-slate-300 mx-auto mb-2"></i>
        <h4 class="text-sm font-bold text-slate-700">No applications submitted yet</h4>
        <p class="text-xs text-slate-400 mt-1">Browse our wonderful animals and submit your first adoption application!</p>
        <button onclick="switchAdopterTab('browse')" class="mt-3 px-3 py-1.5 text-xs bg-orange-600 text-white font-semibold rounded-lg hover:bg-orange-700">Browse Pets</button>
      </div>
    `;
    lucide.createIcons();
    return;
  }

  container.innerHTML = myApps.map(app => `
    <div class="p-5 rounded-xl border border-slate-200 bg-white hover:border-orange-200 transition-colors shadow-sm">
      <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-slate-100 pb-3 mb-3">
        <div>
          <div class="flex items-center gap-2">
            <span class="font-mono text-xs font-bold text-orange-600 bg-orange-50 px-2 py-0.5 rounded border border-orange-200 flex items-center gap-1 cursor-pointer" onclick="copyAppId('${app.id}', event)" title="Click to copy ID">
              ${app.id} <i data-lucide="copy" class="w-3 h-3 text-orange-400"></i>
            </span>
            <h3 class="text-sm font-bold text-slate-800">${app.petName} (${app.petBreed})</h3>
          </div>
          <span class="text-[11px] text-slate-400 mt-0.5 block">Submitted on ${app.submittedDate}</span>
        </div>

        <div class="flex items-center gap-2">
          <span class="px-2.5 py-1 rounded-full text-xs font-bold ${
            app.status === 'Approved' ? 'badge-available' :
            app.status === 'Under Review' ? 'badge-review' :
            app.status === 'Rejected' ? 'badge-rejected' : 'badge-pending'
          }">
            ● ${app.status}
          </span>
          <button onclick="advanceSpecificApp('${app.id}')" class="px-2.5 py-1 text-[11px] font-semibold text-slate-500 hover:text-orange-600 bg-slate-50 hover:bg-orange-50 border border-slate-200 rounded-lg transition-colors" title="Simulate progressing to next step">
            Advance Step ⏩
          </button>
        </div>
      </div>

      <!-- Application Tracking Stepper -->
      <div class="py-2">
        <div class="text-[11px] font-semibold text-slate-500 uppercase tracking-wider mb-2">Live Progress Tracker</div>
        <div class="grid grid-cols-2 sm:grid-cols-4 gap-2 text-center text-xs">
          ${(app.timeline || []).map(step => `
            <div class="p-2.5 rounded-lg border ${
              step.status === 'done' ? 'tracker-step-done font-semibold' :
              step.status === 'current' ? 'tracker-step-active font-bold' :
              'tracker-step-pending'
            }">
              <div class="text-[10px] uppercase font-bold tracking-tight">${step.step}</div>
              <div class="text-[11px] mt-0.5">${step.date}</div>
            </div>
          `).join("")}
        </div>
      </div>

      <div class="mt-3 pt-3 border-t border-slate-100 text-xs text-slate-600 flex flex-wrap items-center justify-between gap-2">
        <div>
          <span class="text-slate-400">Housing:</span> ${app.housingType} &bull; 
          <span class="text-slate-400">Other Pets:</span> ${app.otherPets}
        </div>
        <div class="text-[11px] text-slate-400 italic">
          "${app.reason.substring(0, 60)}${app.reason.length > 60 ? '...' : ''}"
        </div>
      </div>
    </div>
  `).join("");

  lucide.createIcons();
}

function advanceSpecificApp(appId) {
  const app = state.applications.find(a => a.id === appId);
  if (!app) return;

  if (app.status === "Pending") {
    app.status = "Under Review";
    app.timeline[0].status = "done";
    app.timeline[1].status = "current";
    showToast(`Application ${appId} progressed to "Under Review".`, "info");
  } else if (app.status === "Under Review") {
    app.status = "Approved";
    app.timeline[1].status = "done";
    app.timeline[2].status = "done";
    app.timeline[3].status = "current";
    triggerConfetti();
    showToast(`Application ${appId} Approved! Home check passed! 🎉`, "success");
  } else if (app.status === "Approved") {
    app.status = "Completed";
    app.timeline[3].status = "done";
    triggerConfetti();
    showToast(`Adoption ${appId} Completed! Welcome to the family! 🏆`, "success");
  } else {
    showToast(`Application ${appId} is already completed.`, "info");
  }

  saveState();
  renderAdopterApplications();
}

function lookupApplication() {
  const searchId = (document.getElementById("trackAppIdInput")?.value || "").trim().toUpperCase();
  if (!searchId) {
    showToast("Please enter an Application ID to track.", "error");
    return;
  }

  const app = state.applications.find(a => a.id.toUpperCase() === searchId);
  if (!app) {
    showToast(`No application found with ID "${searchId}".`, "error");
    return;
  }

  showToast(`Found Application ${app.id} for ${app.petName}: Current status is "${app.status}".`, "info");
}

// Adopter Messages Tab
function renderAdopterChat() {
  const stream = document.getElementById("adopterChatMessagesStream");
  if (!stream) return;

  stream.innerHTML = state.messages.map(msg => `
    <div class="flex flex-col ${msg.sender === 'adopter' ? 'items-end' : 'items-start'}">
      <div class="max-w-xs md:max-w-md px-3.5 py-2.5 rounded-2xl text-xs ${
        msg.sender === 'adopter' ? 'chat-bubble-out' : 'chat-bubble-in'
      }">
        ${msg.text}
      </div>
      <span class="text-[10px] text-slate-400 mt-1 px-1">${msg.time}</span>
    </div>
  `).join("");

  stream.scrollTop = stream.scrollHeight;
}

function sendAdopterMessage(event) {
  event.preventDefault();
  const input = document.getElementById("adopterChatInput");
  const text = input.value.trim();
  if (!text) return;

  const newMsg = {
    id: "msg-" + Date.now(),
    sender: "adopter",
    applicantName: state.adopterProfile.name,
    text,
    time: "Just now"
  };

  state.messages.push(newMsg);
  saveState();
  input.value = "";

  showToast("Confirmation: Message sent to Happy Paws Shelter!", "success");
  renderAdopterChat();
  renderShelterChat();
}

// Render Adopter History & Certificates
function renderAdopterHistory() {
  const container = document.getElementById("adopterHistoryList");
  if (!container) return;

  container.innerHTML = state.history.map(item => `
    <div class="flex gap-4 p-4 rounded-xl border border-slate-200 bg-white shadow-sm flex-col sm:flex-row">
      <img src="${item.image}" alt="${item.petName}" class="w-full sm:w-28 h-28 rounded-lg object-cover shrink-0">
      <div class="flex-1 text-xs flex flex-col justify-between">
        <div>
          <div class="flex items-center justify-between mb-1">
            <h3 class="font-bold text-slate-900 text-sm">${item.petName}</h3>
            <span class="px-2 py-0.5 rounded-full bg-purple-100 text-purple-700 font-bold text-[10px]">Adopted</span>
          </div>
          <div class="text-slate-500 font-medium">${item.breed} &bull; ${item.type}</div>
          <div class="text-[11px] text-slate-400 mt-1">Adopted on ${item.adoptedDate} from ${item.shelterName}</div>
          <p class="text-slate-600 mt-2 text-[11px] italic bg-slate-50 p-2 rounded-lg border border-slate-100">
            "${item.story}"
          </p>
        </div>
        <button onclick="openCertModal('${item.id}')" class="mt-3 px-3 py-1.5 bg-amber-50 hover:bg-amber-100 text-amber-900 border border-amber-200 text-xs font-bold rounded-lg flex items-center gap-1.5 self-start transition-colors">
          <i data-lucide="award" class="w-3.5 h-3.5 text-amber-600"></i> View Adoption Certificate
        </button>
      </div>
    </div>
  `).join("");

  lucide.createIcons();
}

function openCertModal(histId) {
  const item = state.history.find(h => h.id === histId) || state.history[0];
  document.getElementById("certPetName").innerText = item.petName;
  document.getElementById("certDate").innerText = item.adoptedDate;
  document.getElementById("certShelter").innerText = item.shelterName;
  document.getElementById("certAdopterName").innerText = state.adopterProfile.name;
  document.getElementById("certificateModal").classList.remove("hidden");
  lucide.createIcons();
}

function closeCertModal() {
  document.getElementById("certificateModal").classList.add("hidden");
}

function printOrSaveCert() {
  window.print();
}

function loadAdopterProfile() {
  document.getElementById("prof-name").value = state.adopterProfile.name;
  document.getElementById("prof-email").value = state.adopterProfile.email;
  document.getElementById("prof-phone").value = state.adopterProfile.phone;
  document.getElementById("prof-city").value = state.adopterProfile.city;
  document.getElementById("prof-address").value = state.adopterProfile.address;
  document.getElementById("prof-residence").value = state.adopterProfile.residence;
  document.getElementById("prof-experience").value = state.adopterProfile.experience;
  document.getElementById("prof-bio").value = state.adopterProfile.bio;
}

function saveAdopterProfile(event) {
  event.preventDefault();
  state.adopterProfile = {
    name: document.getElementById("prof-name").value.trim(),
    email: document.getElementById("prof-email").value.trim(),
    phone: document.getElementById("prof-phone").value.trim(),
    city: document.getElementById("prof-city").value.trim(),
    address: document.getElementById("prof-address").value.trim(),
    residence: document.getElementById("prof-residence").value,
    experience: document.getElementById("prof-experience").value,
    bio: document.getElementById("prof-bio").value.trim()
  };

  saveState();
  document.getElementById("currentUserName").innerText = state.adopterProfile.name;
  document.getElementById("adopter-current-location").innerText = state.adopterProfile.city;

  showToast("Confirmation: Adopter profile details updated successfully!", "success");
}

// ==========================================
// 6. SHELTER CONTROLLER & VIEWS
// ==========================================
function switchShelterTab(tabName) {
  const tabs = ["stats", "listings", "applications", "messages"];
  tabs.forEach(t => {
    const el = document.getElementById(`shelter-subtab-${t}`);
    const btn = document.getElementById(`shelter-tab-${t}`);
    if (el) el.classList.toggle("hidden", t !== tabName);
    if (btn) {
      if (t === tabName) {
        btn.className = "shelter-nav-btn px-4 py-2 text-sm font-semibold rounded-lg bg-teal-700 text-white flex items-center gap-2";
      } else {
        btn.className = "shelter-nav-btn px-4 py-2 text-sm font-semibold rounded-lg text-slate-600 hover:bg-slate-100 flex items-center gap-2";
      }
    }
  });

  if (tabName === "stats") updateShelterStats();
  if (tabName === "listings") renderShelterListings();
  if (tabName === "applications") renderShelterApplications();
  if (tabName === "messages") renderShelterChat();
  lucide.createIcons();
}

function updateShelterStats() {
  const shelterPets = state.pets.filter(p => p.shelterId === "sh-1");
  const pendingApps = state.applications.filter(a => a.shelterId === "sh-1" && (a.status === "Pending" || a.status === "Under Review"));
  const completedApps = state.applications.filter(a => a.shelterId === "sh-1" && a.status === "Completed");

  document.getElementById("shelter-stat-active").innerText = shelterPets.length;
  document.getElementById("shelter-stat-pending").innerText = pendingApps.length;
  document.getElementById("shelter-stat-completed").innerText = 18 + completedApps.length;
  document.getElementById("shelter-pet-count").innerText = shelterPets.length;
  document.getElementById("shelter-app-pending-count").innerText = pendingApps.length;
}

function renderShelterListings() {
  const tableBody = document.getElementById("shelterPetTableBody");
  if (!tableBody) return;

  const shelterPets = state.pets.filter(p => p.shelterId === "sh-1");

  tableBody.innerHTML = shelterPets.map(pet => `
    <tr class="hover:bg-slate-50 transition-colors">
      <td class="py-3 px-4 flex items-center gap-3">
        <img src="${pet.image}" alt="${pet.name}" class="w-10 h-10 rounded-lg object-cover">
        <div>
          <div class="font-bold text-slate-900">${pet.name}</div>
          <div class="text-[10px] text-slate-400">ID: ${pet.id}</div>
        </div>
      </td>
      <td class="py-3 px-4">
        <div>${pet.type}</div>
        <div class="text-[11px] text-slate-400">${pet.breed}</div>
      </td>
      <td class="py-3 px-4">
        <div>${pet.age}</div>
        <div class="text-[11px] text-slate-400">${pet.gender}</div>
      </td>
      <td class="py-3 px-4">
        <span class="px-2 py-0.5 rounded-full text-[10px] font-bold ${
          pet.approvalStatus === 'Approved' ? 'bg-emerald-100 text-emerald-800' :
          pet.approvalStatus === 'Rejected' ? 'bg-rose-100 text-rose-800' : 'bg-amber-100 text-amber-800'
        }">
          ${pet.approvalStatus}
        </span>
      </td>
      <td class="py-3 px-4">
        <select onchange="changePetAdoptionStatus('${pet.id}', this.value)" class="text-[11px] px-2 py-1 rounded border border-slate-200 bg-white font-medium">
          <option value="Available" ${pet.adoptionStatus === 'Available' ? 'selected' : ''}>Available</option>
          <option value="Pending" ${pet.adoptionStatus === 'Pending' ? 'selected' : ''}>Pending</option>
          <option value="Adopted" ${pet.adoptionStatus === 'Adopted' ? 'selected' : ''}>Adopted</option>
        </select>
      </td>
      <td class="py-3 px-4 text-right">
        <button onclick="deletePetListing('${pet.id}')" class="text-rose-600 hover:text-rose-800 p-1 rounded hover:bg-rose-50" title="Delete Pet">
          <i data-lucide="trash-2" class="w-4 h-4"></i>
        </button>
      </td>
    </tr>
  `).join("");

  lucide.createIcons();
}

function changePetAdoptionStatus(petId, newStatus) {
  const pet = state.pets.find(p => p.id === petId);
  if (pet) {
    pet.adoptionStatus = newStatus;
    saveState();
    showToast(`Status for "${pet.name}" updated to "${newStatus}".`, "success");
    updateShelterStats();
  }
}

function deletePetListing(petId) {
  if (confirm("Are you sure you want to remove this pet listing?")) {
    state.pets = state.pets.filter(p => p.id !== petId);
    saveState();
    showToast("Pet listing successfully removed.", "success");
    renderShelterListings();
    updateShelterStats();
  }
}

function openAddPetModal() {
  document.getElementById("addPetModal").classList.remove("hidden");
  lucide.createIcons();
}

function closeAddPetModal() {
  document.getElementById("addPetModal").classList.add("hidden");
}

function fillPetPreset(type) {
  if (type === "pup") {
    document.getElementById("newPetName").value = "Rusty";
    document.getElementById("newPetType").value = "Dog";
    document.getElementById("newPetBreed").value = "Golden Retriever Puppy";
    document.getElementById("newPetAge").value = "5 months";
    document.getElementById("newPetGender").value = "Male";
    document.getElementById("newPetImage").value = "https://images.unsplash.com/photo-1591160690555-57bfbe2e5d39?auto=format&fit=crop&w=700&q=80";
    document.getElementById("newPetLocation").value = "Seattle, WA";
    document.getElementById("newPetGoodWith").value = "Kids, other dogs, eager to learn";
    document.getElementById("newPetDescription").value = "Rusty is a sweet golden pup who loves splashing in water, snuggling after playtime, and learning basic recall!";
  } else if (type === "cat") {
    document.getElementById("newPetName").value = "Cleo";
    document.getElementById("newPetType").value = "Cat";
    document.getElementById("newPetBreed").value = "Persian / Longhair Mix";
    document.getElementById("newPetAge").value = "2 years";
    document.getElementById("newPetGender").value = "Female";
    document.getElementById("newPetImage").value = "https://images.unsplash.com/photo-1518791841217-8f162f1e1131?auto=format&fit=crop&w=700&q=80";
    document.getElementById("newPetLocation").value = "Seattle, WA";
    document.getElementById("newPetGoodWith").value = "Quiet households, gentle older kids";
    document.getElementById("newPetDescription").value = "Cleo is a silky purr-machine who enjoys gentle grooming, napping on soft blankets, and catching sunbeams.";
  } else if (type === "bunny") {
    document.getElementById("newPetName").value = "Carrot";
    document.getElementById("newPetType").value = "Rabbit";
    document.getElementById("newPetBreed").value = "Mini Lop";
    document.getElementById("newPetAge").value = "1 year";
    document.getElementById("newPetGender").value = "Male";
    document.getElementById("newPetImage").value = "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=700&q=80";
    document.getElementById("newPetLocation").value = "Seattle, WA";
    document.getElementById("newPetGoodWith").value = "Calm apartments, indoor setup";
    document.getElementById("newPetDescription").value = "Carrot loves cilantro, jumping over low obstacles, and receiving forehead pets.";
  }
  showToast(`Sample preset "${type}" applied! Ready to submit.`, "info");
}

function saveNewPetListing(event) {
  event.preventDefault();
  const name = document.getElementById("newPetName").value.trim();
  const type = document.getElementById("newPetType").value;
  const breed = document.getElementById("newPetBreed").value.trim();
  const age = document.getElementById("newPetAge").value.trim();
  const gender = document.getElementById("newPetGender").value;
  let image = document.getElementById("newPetImage").value.trim();
  const location = document.getElementById("newPetLocation").value.trim() || "Seattle, WA";
  const goodWith = document.getElementById("newPetGoodWith").value.trim();
  const description = document.getElementById("newPetDescription").value.trim();

  if (!image) {
    if (type === "Dog") image = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=700&q=80";
    else if (type === "Cat") image = "https://images.unsplash.com/photo-1574158622682-e40e69881006?auto=format&fit=crop&w=700&q=80";
    else if (type === "Rabbit") image = "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=700&q=80";
    else image = "https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=700&q=80";
  }

  const newPet = {
    id: "pet-" + Math.floor(200 + Math.random() * 800),
    name,
    type,
    breed,
    age,
    gender,
    location,
    shelterId: "sh-1",
    shelterName: "Happy Paws Rescue & Shelter",
    image,
    description,
    vaccinations: "Up to Date",
    goodWith,
    approvalStatus: state.settings.requireApproval ? "Pending" : "Approved",
    adoptionStatus: "Available",
    submittedDate: new Date().toISOString().split("T")[0]
  };

  state.pets.unshift(newPet);
  saveState();
  closeAddPetModal();

  const msg = state.settings.requireApproval 
    ? `Confirmation: "${name}" listed successfully! Awaiting Admin approval.`
    : `Confirmation: "${name}" listed and published live!`;
  
  showToast(msg, "success");
  renderShelterListings();
  updateShelterStats();
}

function renderShelterApplications() {
  const container = document.getElementById("shelterApplicationsContainer");
  if (!container) return;

  const shelterApps = state.applications.filter(a => a.shelterId === "sh-1");

  if (shelterApps.length === 0) {
    container.innerHTML = `
      <div class="p-8 text-center bg-slate-50 rounded-xl border border-dashed border-slate-200">
        <i data-lucide="inbox" class="w-10 h-10 text-slate-300 mx-auto mb-2"></i>
        <h4 class="text-sm font-bold text-slate-700">No applications received yet</h4>
        <p class="text-xs text-slate-400 mt-1">Applications submitted by potential adopters will appear here for review.</p>
      </div>
    `;
    lucide.createIcons();
    return;
  }

  container.innerHTML = shelterApps.map(app => `
    <div class="p-5 rounded-xl border border-slate-200 bg-white shadow-sm space-y-3">
      <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-slate-100 pb-3">
        <div>
          <div class="flex items-center gap-2">
            <span class="font-mono text-xs font-bold text-teal-700 bg-teal-50 px-2 py-0.5 rounded border border-teal-200 cursor-pointer" onclick="copyAppId('${app.id}', event)">
              ${app.id} <i data-lucide="copy" class="w-3 h-3 text-teal-500 inline"></i>
            </span>
            <span class="text-sm font-bold text-slate-900">${app.applicantName}</span>
            <span class="text-xs text-slate-400">&bull; Applied for <strong class="text-slate-700">${app.petName}</strong></span>
          </div>
          <div class="text-[11px] text-slate-400 mt-0.5">${app.applicantEmail} • Submitted ${app.submittedDate}</div>
        </div>

        <div class="flex items-center gap-2">
          <span class="px-2.5 py-1 rounded-full text-xs font-bold ${
            app.status === 'Approved' ? 'badge-available' :
            app.status === 'Under Review' ? 'badge-review' :
            app.status === 'Rejected' ? 'badge-rejected' : 'badge-pending'
          }">
            ${app.status}
          </span>
        </div>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-3 text-xs bg-slate-50 p-3 rounded-lg">
        <div><strong class="text-slate-600">Housing Type:</strong> ${app.housingType}</div>
        <div><strong class="text-slate-600">Other Pets:</strong> ${app.otherPets}</div>
        <div class="col-span-full"><strong class="text-slate-600">Applicant Notes:</strong> ${app.reason}</div>
      </div>

      <div class="flex flex-wrap items-center justify-end gap-2 pt-2 border-t border-slate-100">
        <button onclick="updateApplicationStatus('${app.id}', 'Under Review')" class="px-3 py-1.5 text-xs font-semibold rounded-lg bg-sky-50 text-sky-700 hover:bg-sky-100 transition-colors">
          Mark Under Review
        </button>
        <button onclick="updateApplicationStatus('${app.id}', 'Rejected')" class="px-3 py-1.5 text-xs font-semibold rounded-lg bg-rose-50 text-rose-700 hover:bg-rose-100 transition-colors">
          Reject
        </button>
        <button onclick="updateApplicationStatus('${app.id}', 'Approved')" class="px-3 py-1.5 text-xs font-semibold rounded-lg bg-emerald-600 text-white hover:bg-emerald-700 transition-colors flex items-center gap-1">
          <i data-lucide="check" class="w-3.5 h-3.5"></i> Approve Application
        </button>
      </div>
    </div>
  `).join("");

  lucide.createIcons();
}

function updateApplicationStatus(appId, newStatus) {
  const app = state.applications.find(a => a.id === appId);
  if (!app) return;

  app.status = newStatus;
  if (app.timeline && app.timeline.length >= 2) {
    if (newStatus === "Under Review") {
      app.timeline[1].status = "current";
    } else if (newStatus === "Approved") {
      app.timeline[1].status = "done";
      app.timeline[2].status = "done";
      app.timeline[3].status = "current";
      triggerConfetti();
    }
  }

  saveState();
  showToast(`Application ${appId} status updated to "${newStatus}".`, "success");
  renderShelterApplications();
  updateShelterStats();
}

function renderShelterChat() {
  const contactsList = document.getElementById("chatContactsList");
  const messagesStream = document.getElementById("chatMessagesStream");
  if (!contactsList || !messagesStream) return;

  contactsList.innerHTML = `
    <div class="p-3 bg-white border-l-4 border-teal-600 cursor-pointer">
      <div class="font-bold text-xs text-slate-800">Sarah Jenkins</div>
      <div class="text-[11px] text-slate-500">Inquiry for Barnaby</div>
      <div class="text-[10px] text-teal-600 font-medium mt-1">Active Conversation</div>
    </div>
    <div class="p-3 hover:bg-slate-100 cursor-pointer opacity-70">
      <div class="font-bold text-xs text-slate-800">David Martinez</div>
      <div class="text-[11px] text-slate-500">Inquiry for Milo</div>
      <div class="text-[10px] text-slate-400 mt-1">Application Approved</div>
    </div>
  `;

  messagesStream.innerHTML = state.messages.map(msg => `
    <div class="flex flex-col ${msg.sender === 'shelter' ? 'items-end' : 'items-start'}">
      <div class="max-w-xs md:max-w-md px-3.5 py-2.5 rounded-2xl text-xs ${
        msg.sender === 'shelter' ? 'chat-bubble-out' : 'chat-bubble-in'
      }">
        ${msg.text}
      </div>
      <span class="text-[10px] text-slate-400 mt-1 px-1">${msg.time}</span>
    </div>
  `).join("");

  messagesStream.scrollTop = messagesStream.scrollHeight;
}

function sendShelterMessage(event) {
  event.preventDefault();
  const input = document.getElementById("chatMessageInput");
  const text = input.value.trim();
  if (!text) return;

  const newMsg = {
    id: "msg-" + Date.now(),
    sender: "shelter",
    applicantName: "Sarah Jenkins",
    text,
    time: "Just now"
  };

  state.messages.push(newMsg);
  saveState();
  input.value = "";

  showToast("Confirmation: Message delivered successfully to adopter!", "success");
  renderShelterChat();
  renderAdopterChat();
}

// ==========================================
// 7. ADMIN CONTROLLER & VIEWS
// ==========================================
function switchAdminTab(tabName) {
  const tabs = ["analytics", "users", "approvals", "settings"];
  tabs.forEach(t => {
    const el = document.getElementById(`admin-subtab-${t}`);
    const btn = document.getElementById(`admin-tab-${t}`);
    if (el) el.classList.toggle("hidden", t !== tabName);
    if (btn) {
      if (t === tabName) {
        btn.className = "admin-nav-btn px-4 py-2 text-sm font-semibold rounded-lg bg-purple-700 text-white flex items-center gap-2";
      } else {
        btn.className = "admin-nav-btn px-4 py-2 text-sm font-semibold rounded-lg text-slate-600 hover:bg-slate-100 flex items-center gap-2";
      }
    }
  });

  if (tabName === "analytics") renderAdminAnalytics();
  if (tabName === "users") renderAdminUsers();
  if (tabName === "approvals") renderAdminApprovals();
  if (tabName === "settings") loadSystemSettings();
  lucide.createIcons();
}

function renderAdminUsers() {
  filterUsersTable();
}

function filterUsersTable() {
  const search = (document.getElementById("userSearchInput")?.value || "").toLowerCase();
  const roleFilter = document.getElementById("userRoleFilter")?.value || "ALL";

  const filtered = state.users.filter(u => {
    const matchesSearch = !search || u.name.toLowerCase().includes(search) || u.email.toLowerCase().includes(search);
    const matchesRole = roleFilter === "ALL" || u.role === roleFilter;
    return matchesSearch && matchesRole;
  });

  const tbody = document.getElementById("adminUsersTableBody");
  if (!tbody) return;

  tbody.innerHTML = filtered.map(u => `
    <tr class="hover:bg-slate-50 transition-colors">
      <td class="py-3 px-4">
        <div class="font-bold text-slate-800">${u.name}</div>
        <div class="text-[10px] text-slate-400">ID: ${u.id}</div>
      </td>
      <td class="py-3 px-4">${u.email}</td>
      <td class="py-3 px-4">
        <button onclick="cycleUserRole('${u.id}')" class="px-2.5 py-0.5 rounded-full text-[10px] font-bold transition-transform hover:scale-105 ${
          u.role === 'Admin' ? 'bg-purple-100 text-purple-800' :
          u.role === 'Shelter' ? 'bg-teal-100 text-teal-800' : 'bg-orange-100 text-orange-800'
        }" title="Click to cycle role">
          ${u.role} 🔄
        </button>
      </td>
      <td class="py-3 px-4">
        <button onclick="toggleUserStatus('${u.id}')" class="px-2.5 py-0.5 rounded-full text-[10px] font-semibold transition-transform hover:scale-105 ${
          u.status === 'Active' ? 'bg-emerald-100 text-emerald-800' : 'bg-rose-100 text-rose-700'
        }" title="Click to toggle status">
          ${u.status}
        </button>
      </td>
      <td class="py-3 px-4">${u.createdDate}</td>
      <td class="py-3 px-4 text-right">
        <button onclick="deleteUser('${u.id}')" class="text-rose-600 hover:text-rose-800 p-1 rounded hover:bg-rose-50" title="Delete User">
          <i data-lucide="trash-2" class="w-4 h-4"></i>
        </button>
      </td>
    </tr>
  `).join("");

  lucide.createIcons();
}

function cycleUserRole(userId) {
  const user = state.users.find(u => u.id === userId);
  if (!user) return;

  const roles = ["Adopter", "Shelter", "Admin"];
  const nextIdx = (roles.indexOf(user.role) + 1) % roles.length;
  user.role = roles[nextIdx];
  saveState();

  showToast(`Role for "${user.name}" updated to "${user.role}".`, "info");
  renderAdminUsers();
}

function toggleUserStatus(userId) {
  const user = state.users.find(u => u.id === userId);
  if (!user) return;

  user.status = user.status === "Active" ? "Suspended" : "Active";
  saveState();

  showToast(`Status for "${user.name}" is now "${user.status}".`, "info");
  renderAdminUsers();
}

function openAddUserModal() {
  document.getElementById("addUserModal").classList.remove("hidden");
  lucide.createIcons();
}

function closeAddUserModal() {
  document.getElementById("addUserModal").classList.add("hidden");
}

function saveNewUser(event) {
  event.preventDefault();
  const name = document.getElementById("newUserName").value.trim();
  const email = document.getElementById("newUserEmail").value.trim();
  const role = document.getElementById("newUserRole").value;
  const status = document.getElementById("newUserStatus").value;

  const newUser = {
    id: "usr-" + Math.floor(100 + Math.random() * 900),
    name,
    email,
    role,
    status,
    createdDate: new Date().toISOString().split("T")[0]
  };

  state.users.unshift(newUser);
  saveState();
  closeAddUserModal();

  showToast(`Confirmation: User account for "${name}" created successfully!`, "success");
  renderAdminUsers();
}

function deleteUser(userId) {
  const user = state.users.find(u => u.id === userId);
  if (!user) return;

  if (confirm(`Are you sure you want to delete user "${user.name}"?`)) {
    state.users = state.users.filter(u => u.id !== userId);
    saveState();
    showToast(`Confirmation: User account "${user.name}" deleted successfully.`, "success");
    renderAdminUsers();
  }
}

function renderAdminApprovals() {
  const container = document.getElementById("adminApprovalsContainer");
  const countBadge = document.getElementById("admin-pending-approval-count");
  if (!container) return;

  const pendingPets = state.pets.filter(p => p.approvalStatus === "Pending");
  if (countBadge) countBadge.innerText = pendingPets.length;

  const statPending = document.getElementById("admin-stat-pending");
  if (statPending) statPending.innerText = pendingPets.length;

  if (pendingPets.length === 0) {
    container.innerHTML = `
      <div class="p-8 text-center bg-slate-50 rounded-xl border border-dashed border-slate-200">
        <i data-lucide="check-circle" class="w-10 h-10 text-emerald-500 mx-auto mb-2"></i>
        <h4 class="text-sm font-bold text-slate-700">All pet submissions reviewed!</h4>
        <p class="text-xs text-slate-400 mt-1">There are no pending pet listings awaiting approval at this time.</p>
      </div>
    `;
    lucide.createIcons();
    return;
  }

  container.innerHTML = pendingPets.map(pet => `
    <div class="p-5 rounded-xl border border-slate-200 bg-white shadow-sm flex flex-col md:flex-row gap-4 items-start">
      <img src="${pet.image}" alt="${pet.name}" class="w-32 h-32 rounded-xl object-cover shrink-0">
      
      <div class="flex-1 space-y-2 text-xs">
        <div class="flex items-center justify-between">
          <div>
            <h3 class="text-base font-bold text-slate-900">${pet.name}</h3>
            <div class="text-slate-500 font-medium">${pet.type} &bull; ${pet.breed} &bull; ${pet.age} &bull; ${pet.gender}</div>
          </div>
          <span class="px-2.5 py-1 rounded-full text-xs font-bold badge-pending">
            Awaiting Admin Review
          </span>
        </div>

        <p class="text-slate-600 leading-relaxed">${pet.description}</p>

        <div class="flex items-center gap-4 text-slate-500 pt-1">
          <div><strong>Shelter:</strong> ${pet.shelterName}</div>
          <div><strong>Submitted:</strong> ${pet.submittedDate}</div>
        </div>

        <div class="flex items-center justify-end gap-2 pt-2 border-t border-slate-100">
          <button onclick="adminReviewListing('${pet.id}', 'Rejected')" class="px-4 py-2 bg-rose-50 hover:bg-rose-100 text-rose-700 font-semibold rounded-lg transition-colors">
            Reject Listing
          </button>
          <button onclick="adminReviewListing('${pet.id}', 'Approved')" class="px-5 py-2 bg-purple-600 hover:bg-purple-700 text-white font-semibold rounded-lg shadow-sm transition-colors flex items-center gap-1.5">
            <i data-lucide="check" class="w-3.5 h-3.5"></i> Approve & Publish Live
          </button>
        </div>
      </div>
    </div>
  `).join("");

  lucide.createIcons();
}

function adminReviewListing(petId, decision) {
  const pet = state.pets.find(p => p.id === petId);
  if (!pet) return;

  pet.approvalStatus = decision;
  saveState();

  if (decision === "Approved") triggerConfetti();
  showToast(`Confirmation: Pet listing "${pet.name}" status updated to "${decision}".`, decision === "Approved" ? "success" : "info");
  renderAdminApprovals();
}

function approveAllPendingPets() {
  const pending = state.pets.filter(p => p.approvalStatus === "Pending");
  if (pending.length === 0) {
    showToast("No pending pet listings to approve.", "info");
    return;
  }

  pending.forEach(p => p.approvalStatus = "Approved");
  saveState();
  triggerConfetti();
  showToast(`All ${pending.length} pending pet listings approved and published!`, "success");
  renderAdminApprovals();
}

function loadSystemSettings() {
  document.getElementById("setting-plat-name").value = state.settings.platformName;
  document.getElementById("setting-support-email").value = state.settings.supportEmail;
  document.getElementById("setting-adoption-fee").value = state.settings.adoptionFee;
  document.getElementById("setting-max-listings").value = state.settings.maxListings;
  document.getElementById("setting-auto-archive").value = state.settings.autoArchiveDays;
  document.getElementById("setting-require-approval").checked = state.settings.requireApproval;
  document.getElementById("setting-email-alerts").checked = state.settings.emailAlerts;
}

function saveSystemSettings(event) {
  event.preventDefault();
  state.settings = {
    platformName: document.getElementById("setting-plat-name").value.trim(),
    supportEmail: document.getElementById("setting-support-email").value.trim(),
    adoptionFee: Number(document.getElementById("setting-adoption-fee").value),
    maxListings: Number(document.getElementById("setting-max-listings").value),
    autoArchiveDays: Number(document.getElementById("setting-auto-archive").value),
    requireApproval: document.getElementById("setting-require-approval").checked,
    emailAlerts: document.getElementById("setting-email-alerts").checked
  };

  saveState();
  showToast("Confirmation: System configuration settings updated successfully!", "success");
}

let trendChartInstance = null;
let pieChartInstance = null;

function renderAdminAnalytics() {
  document.getElementById("admin-stat-users").innerText = state.users.length.toLocaleString();
  document.getElementById("admin-stat-pets").innerText = state.pets.length.toLocaleString();
  document.getElementById("admin-stat-pending").innerText = state.pets.filter(p => p.approvalStatus === "Pending").length;

  const trendCtx = document.getElementById("adoptionTrendChart")?.getContext("2d");
  const pieCtx = document.getElementById("petTypePieChart")?.getContext("2d");

  if (trendCtx) {
    if (trendChartInstance) trendChartInstance.destroy();
    trendChartInstance = new Chart(trendCtx, {
      type: "line",
      data: {
        labels: ["May", "Jun", "Jul", "Aug", "Sep", "Oct"],
        datasets: [{
          label: "Adoptions Completed",
          data: [28, 35, 42, 38, 54, 62],
          borderColor: "#9333ea",
          backgroundColor: "rgba(147, 51, 234, 0.1)",
          fill: true,
          tension: 0.35,
          borderWidth: 2
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: {
          y: { beginAtZero: true, grid: { color: "#f1f5f9" } },
          x: { grid: { display: false } }
        }
      }
    });
  }

  if (pieCtx) {
    if (pieChartInstance) pieChartInstance.destroy();
    const dogs = state.pets.filter(p => p.type === "Dog").length;
    const cats = state.pets.filter(p => p.type === "Cat").length;
    const rabbits = state.pets.filter(p => p.type === "Rabbit").length;
    const birds = state.pets.filter(p => p.type === "Bird").length;

    pieChartInstance = new Chart(pieCtx, {
      type: "doughnut",
      data: {
        labels: ["Dogs", "Cats", "Rabbits", "Birds"],
        datasets: [{
          data: [dogs, cats, rabbits, birds],
          backgroundColor: ["#f97316", "#0d9488", "#8b5cf6", "#3b82f6"],
          borderWidth: 2,
          borderColor: "#ffffff"
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { position: "bottom", labels: { boxWidth: 12, font: { size: 11 } } }
        }
      }
    });
  }
}

// ==========================================
// 8. 1-CLICK INTERACTIVE DEMO SCENARIOS
// ==========================================
function openTourModal() {
  document.getElementById("tourModal").classList.remove("hidden");
  lucide.createIcons();
}

function closeTourModal() {
  document.getElementById("tourModal").classList.add("hidden");
}

function runAdopterDemoTour() {
  closeTourModal();
  switchRole("adopter");
  switchAdopterTab("browse");
  setTimeout(() => {
    openPetDetailModal("pet-101");
    autoFillSampleApplication();
    showToast("Adopter Flow: Pre-filled Barnaby's adoption form! Ready to submit.", "info");
  }, 300);
}

function runShelterDemoTour() {
  closeTourModal();
  switchRole("shelter");
  switchShelterTab("applications");
  showToast("Shelter Flow: Opened Adoption Applications. You can approve or review with 1 click!", "info");
}

function runAdminDemoTour() {
  closeTourModal();
  switchRole("admin");
  switchAdminTab("approvals");
  showToast("Admin Flow: Review queue opened. Ranger (Puppy) is awaiting your approval!", "info");
}

function simulateAdvanceStatus() {
  closeTourModal();
  switchRole("adopter");
  switchAdopterTab("applications");
  const targetApp = state.applications[0];
  if (targetApp) {
    advanceSpecificApp(targetApp.id);
  }
}

// ==========================================
// 9. GLOBAL INITIALIZATION
// ==========================================
document.addEventListener("DOMContentLoaded", () => {
  lucide.createIcons();
  switchRole("adopter");
});
