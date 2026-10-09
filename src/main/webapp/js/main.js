/**
 * PawHaven - Client-side interactivity helpers & dynamic UI scripts
 */

document.addEventListener('DOMContentLoaded', function () {
    // 1. Auto dismiss alerts after 6 seconds
    const alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            if (bsAlert) {
                bsAlert.close();
            }
        }, 6000);
    });

    // 2. Demo Login Quick Fill
    const demoButtons = document.querySelectorAll('.demo-fill-btn');
    demoButtons.forEach(function (btn) {
        btn.addEventListener('click', function () {
            const email = this.getAttribute('data-email');
            const password = this.getAttribute('data-password');

            const emailInput = document.getElementById('email');
            const passwordInput = document.getElementById('password');

            if (emailInput && passwordInput) {
                emailInput.value = email;
                passwordInput.value = password;
                emailInput.focus();
            }
        });
    });

    // 3. Application Review Modal Population
    const reviewModal = document.getElementById('reviewModal');
    if (reviewModal) {
        reviewModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            if (!button) return;
            const appId = button.getAttribute('data-app-id');
            const applicantName = button.getAttribute('data-applicant-name');
            const petName = button.getAttribute('data-pet-name');
            const details = button.getAttribute('data-details');

            const modalAppId = document.getElementById('modalAppId');
            const modalApplicantName = document.getElementById('modalApplicantName');
            const modalPetName = document.getElementById('modalPetName');
            const modalDetails = document.getElementById('modalDetails');

            if (modalAppId) modalAppId.value = appId || '';
            if (modalApplicantName) modalApplicantName.textContent = applicantName || '';
            if (modalPetName) modalPetName.textContent = petName || '';
            if (modalDetails) modalDetails.textContent = details || '';
        });
    }

    // 4. Delete & Destructive Action Confirmation Handler
    const confirmButtons = document.querySelectorAll('.confirm-action');
    confirmButtons.forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            const message = this.getAttribute('data-confirm') || 'Are you sure you want to proceed?';
            if (!confirm(message)) {
                e.preventDefault();
            }
        });
    });

    // 5. Initialize 3D UI & Scroll Animations
    initHero3DParallax();
    init3DTilt();
    initScrollReveal();
});

/**
 * 5. Dynamic Animal Type -> Breed Dependent Dropdowns
 * Reusable across Search page and Shelter Pet listing forms.
 * Reads the species-to-breeds catalogue provided by the backend, ensuring zero code changes
 * are needed in the frontend when new animals or breeds are added.
 */
function initSpeciesBreedDropdown(typeElementId, breedElementId, currentBreedVal, breedsMap, isRequired) {
    const typeSelect = document.getElementById(typeElementId);
    const breedSelect = document.getElementById(breedElementId);
    if (!typeSelect || !breedSelect || !breedsMap) return;

    function populateBreeds(selectedType, selectedBreed) {
        breedSelect.innerHTML = '';

        if (!selectedType || selectedType.toUpperCase() === 'ALL') {
            if (isRequired) {
                const opt = document.createElement('option');
                opt.value = '';
                opt.textContent = '-- Select Animal Species First --';
                breedSelect.appendChild(opt);
            } else {
                const allOpt = document.createElement('option');
                allOpt.value = 'ALL';
                allOpt.textContent = 'All Breeds';
                if (!selectedBreed || selectedBreed.toUpperCase() === 'ALL') {
                    allOpt.selected = true;
                }
                breedSelect.appendChild(allOpt);

                // Group all breeds under species headers for convenience
                for (const species in breedsMap) {
                    const optgroup = document.createElement('optgroup');
                    optgroup.label = species + ' Breeds';
                    breedsMap[species].forEach(function (b) {
                        const bOpt = document.createElement('option');
                        bOpt.value = b;
                        bOpt.textContent = b;
                        if (selectedBreed && selectedBreed.toLowerCase() === b.toLowerCase()) {
                            bOpt.selected = true;
                        }
                        optgroup.appendChild(bOpt);
                    });
                    breedSelect.appendChild(optgroup);
                }
            }
        } else {
            // Find breeds for the selected animal species
            let matchedBreeds = [];
            for (const species in breedsMap) {
                if (species.toLowerCase() === selectedType.toLowerCase()) {
                    matchedBreeds = breedsMap[species];
                    break;
                }
            }

            if (!isRequired) {
                const allOpt = document.createElement('option');
                allOpt.value = 'ALL';
                allOpt.textContent = 'All ' + selectedType + ' Breeds';
                if (!selectedBreed || selectedBreed.toUpperCase() === 'ALL') {
                    allOpt.selected = true;
                }
                breedSelect.appendChild(allOpt);
            } else {
                const promptOpt = document.createElement('option');
                promptOpt.value = '';
                promptOpt.textContent = '-- Select ' + selectedType + ' Breed --';
                breedSelect.appendChild(promptOpt);
            }

            matchedBreeds.forEach(function (b) {
                const bOpt = document.createElement('option');
                bOpt.value = b;
                bOpt.textContent = b;
                if (selectedBreed && selectedBreed.toLowerCase() === b.toLowerCase()) {
                    bOpt.selected = true;
                }
                breedSelect.appendChild(bOpt);
            });

            // For shelter forms, allow custom/other breed entry
            if (isRequired) {
                const otherOpt = document.createElement('option');
                otherOpt.value = '__custom__';
                otherOpt.textContent = 'Other / Custom Breed...';
                if (selectedBreed && !matchedBreeds.some(function(b) { return b.toLowerCase() === selectedBreed.toLowerCase(); })) {
                    otherOpt.selected = true;
                }
                breedSelect.appendChild(otherOpt);
            }
        }
    }

    typeSelect.addEventListener('change', function () {
        populateBreeds(this.value, null);
    });

    // Populate immediately with current server-rendered state
    populateBreeds(typeSelect.value, currentBreedVal);
}

// 6. Species-Aware Image Error Handler with Loop Protection
document.addEventListener('error', function (e) {
    if (e.target && e.target.tagName === 'IMG' && (e.target.classList.contains('pet-card-img') || e.target.classList.contains('pet-thumb-img'))) {
        if (!e.target.dataset.fallbackTried) {
            e.target.dataset.fallbackTried = 'true';
            const species = (e.target.getAttribute('data-species') || 'Dog').toLowerCase();
            const fallbacks = {
                dog: 'https://images.unsplash.com/photo-1543466835-00a7907e9de1?auto=format&fit=crop&w=600&q=80',
                cat: 'https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=600&q=80',
                rabbit: 'https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=600&q=80',
                bird: 'https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=600&q=80',
                hamster: 'https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=600&q=80',
                turtle: 'https://images.unsplash.com/photo-1508455858334-95337ba25607?auto=format&fit=crop&w=600&q=80'
            };
            e.target.src = fallbacks[species] || fallbacks.dog;
        }
    }
}, true);

/**
 * 7. Modern 3D Interactive Hero Section Parallax
 * Gently rotates the 3D pet companion card based on cursor coordinates with realistic depth and lighting.
 */
function initHero3DParallax() {
    const stage = document.getElementById('hero3dStage');
    const card = document.getElementById('hero3dCard');
    const glare = document.getElementById('hero3dGlare');
    const shadow = document.getElementById('hero3dShadow');
    const topBadge = document.querySelector('.hero-floating-badge-top');
    const bottomBadge = document.querySelector('.hero-floating-badge-bottom');

    if (!stage || !card) return;

    // Accessibility check: Skip dynamic cursor tracking if user prefers reduced motion
    if (window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
        return;
    }
    // Only track hover coordinates on devices supporting hover pointer (skips touch lag)
    if (window.matchMedia && !window.matchMedia('(hover: hover)').matches) {
        return;
    }

    let isHovering = false;
    let targetX = 0, targetY = 0;
    let currentX = 0, currentY = 0;
    let animFrameId = null;

    function updateParallax() {
        if (!isHovering) {
            currentX += (0 - currentX) * 0.1;
            currentY += (0 - currentY) * 0.1;

            if (Math.abs(currentX) < 0.05 && Math.abs(currentY) < 0.05) {
                currentX = 0;
                currentY = 0;
                card.style.transform = '';
                if (glare) glare.style.opacity = '0.5';
                if (shadow) shadow.style.transform = '';
                if (topBadge) topBadge.style.transform = '';
                if (bottomBadge) bottomBadge.style.transform = '';
                card.style.animationPlayState = 'running';
                animFrameId = null;
                return;
            }
        } else {
            currentX += (targetX - currentX) * 0.12;
            currentY += (targetY - currentY) * 0.12;
        }

        const rotX = (-currentY * 11).toFixed(2);
        const rotY = (currentX * 13).toFixed(2);

        card.style.transform = 'perspective(1000px) rotateX(' + rotX + 'deg) rotateY(' + rotY + 'deg) translateZ(15px)';

        if (glare) {
            const gx = ((currentX + 1) / 2 * 100).toFixed(1);
            const gy = ((currentY + 1) / 2 * 100).toFixed(1);
            glare.style.background = 'radial-gradient(circle at ' + gx + '% ' + gy + '%, rgba(255, 255, 255, 0.55), transparent 65%)';
            glare.style.opacity = '0.85';
        }

        if (shadow) {
            const shadowX = (currentX * 16).toFixed(1);
            shadow.style.transform = 'translateX(' + shadowX + 'px) scale(' + (1 - Math.abs(currentY) * 0.08) + ')';
        }

        if (topBadge) {
            const bx = (-currentX * 16).toFixed(1);
            const by = (-currentY * 16).toFixed(1);
            topBadge.style.transform = 'translate3d(' + bx + 'px, ' + by + 'px, 65px)';
        }

        if (bottomBadge) {
            const bx = (currentX * 20).toFixed(1);
            const by = (currentY * 20).toFixed(1);
            bottomBadge.style.transform = 'translate3d(' + bx + 'px, ' + by + 'px, 75px)';
        }

        animFrameId = requestAnimationFrame(updateParallax);
    }

    stage.addEventListener('mouseenter', function () {
        isHovering = true;
        card.style.animationPlayState = 'paused';
        if (!animFrameId) {
            animFrameId = requestAnimationFrame(updateParallax);
        }
    });

    stage.addEventListener('mousemove', function (e) {
        const rect = stage.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
        const cx = rect.width / 2;
        const cy = rect.height / 2;

        targetX = Math.max(-1, Math.min(1, (x - cx) / cx));
        targetY = Math.max(-1, Math.min(1, (y - cy) / cy));

        if (!animFrameId) {
            animFrameId = requestAnimationFrame(updateParallax);
        }
    });

    stage.addEventListener('mouseleave', function () {
        isHovering = false;
        targetX = 0;
        targetY = 0;
    });
}

/**
 * 8. Modern 3D Interactive Card Tilt
 * Applies smooth physical 3D tilt feedback upon mouse interaction on pet cards, categories, and steps.
 */
function init3DTilt() {
    if (window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
        return;
    }
    if (window.matchMedia && !window.matchMedia('(hover: hover)').matches) {
        return;
    }

    const cards = document.querySelectorAll('.card-paw, .category-card, .step-card, .breed-guide-card, .pet-details-gallery-card, .feed-post-card');

    cards.forEach(function (card) {
        let isCardHovering = false;
        let cardTargetRotX = 0, cardTargetRotY = 0;
        let cardCurRotX = 0, cardCurRotY = 0;
        let cardRaf = null;

        function updateCardTilt() {
            if (!isCardHovering) {
                cardCurRotX += (0 - cardCurRotX) * 0.15;
                cardCurRotY += (0 - cardCurRotY) * 0.15;
                if (Math.abs(cardCurRotX) < 0.1 && Math.abs(cardCurRotY) < 0.1) {
                    card.style.transform = '';
                    cardRaf = null;
                    return;
                }
            } else {
                cardCurRotX += (cardTargetRotX - cardCurRotX) * 0.15;
                cardCurRotY += (cardTargetRotY - cardCurRotY) * 0.15;
            }

            card.style.transform = 'perspective(1000px) rotateX(' + cardCurRotX.toFixed(2) + 'deg) rotateY(' + cardCurRotY.toFixed(2) + 'deg) translateY(-5px) translateZ(10px)';
            cardRaf = requestAnimationFrame(updateCardTilt);
        }

        card.addEventListener('mouseenter', function () {
            isCardHovering = true;
            if (!cardRaf) cardRaf = requestAnimationFrame(updateCardTilt);
        });

        card.addEventListener('mousemove', function (e) {
            const rect = card.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;
            const cx = rect.width / 2;
            const cy = rect.height / 2;

            cardTargetRotX = Math.max(-6, Math.min(6, ((y - cy) / cy) * -6));
            cardTargetRotY = Math.max(-6, Math.min(6, ((x - cx) / cx) * 6));

            if (!cardRaf) cardRaf = requestAnimationFrame(updateCardTilt);
        });

        card.addEventListener('mouseleave', function () {
            isCardHovering = false;
            cardTargetRotX = 0;
            cardTargetRotY = 0;
        });
    });
}

/**
 * 9. Scroll Reveal Animations with IntersectionObserver
 * Elements smoothly glide up into view when entering the viewport.
 */
function initScrollReveal() {
    const revealElements = document.querySelectorAll('.scroll-reveal');
    if (!revealElements.length) return;

    if (!('IntersectionObserver' in window) ||
        (window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches)) {
        revealElements.forEach(function (el) {
            el.classList.add('is-revealed');
        });
        return;
    }

    const observer = new IntersectionObserver(function (entries, obs) {
        entries.forEach(function (entry) {
            if (entry.isIntersecting) {
                const parent = entry.target.parentElement;
                if (parent && parent.classList.contains('row')) {
                    const siblings = Array.from(parent.querySelectorAll('.scroll-reveal'));
                    const idx = siblings.indexOf(entry.target);
                    if (idx > -1) {
                        entry.target.style.transitionDelay = ((idx % 4) * 60) + 'ms';
                    }
                }
                entry.target.classList.add('is-revealed');
                obs.unobserve(entry.target);
            }
        });
    }, {
        rootMargin: '0px 0px -30px 0px',
        threshold: 0.08
    });

    revealElements.forEach(function (el) {
        observer.observe(el);
    });
}
