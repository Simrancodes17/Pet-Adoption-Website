/**
 * PawHaven - Client-side interactivity helpers
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
            const appId = button.getAttribute('data-app-id');
            const applicantName = button.getAttribute('data-applicant-name');
            const petName = button.getAttribute('data-pet-name');
            const details = button.getAttribute('data-details');

            document.getElementById('modalAppId').value = appId;
            document.getElementById('modalApplicantName').textContent = applicantName;
            document.getElementById('modalPetName').textContent = petName;
            document.getElementById('modalDetails').textContent = details;
        });
    }

    // 4. Delete Confirmation Handler
    const deleteButtons = document.querySelectorAll('.confirm-action');
    deleteButtons.forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            const message = this.getAttribute('data-confirm') || 'Are you sure you want to proceed?';
            if (!confirm(message)) {
                e.preventDefault();
            }
        });
    });
});
