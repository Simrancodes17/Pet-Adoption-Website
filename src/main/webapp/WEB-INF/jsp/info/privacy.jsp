<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Privacy Policy | PawHaven" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-9">
            <!-- Header -->
            <div class="mb-5 pb-3 border-bottom">
                <span class="text-uppercase fw-bold text-success small" style="letter-spacing: 0.05em;">Trust &amp; Transparency</span>
                <h1 class="fw-bold mt-1 mb-2">Privacy Policy</h1>
                <p class="text-muted small mb-0">Effective Date: October 2026 | Last Updated: October 2026</p>
            </div>

            <div class="card card-paw p-4 p-md-5 border mb-5">
                <div class="d-flex flex-column gap-4 text-secondary" style="line-height: 1.7;">
                    <section>
                        <h4 class="fw-bold text-dark mb-3">1. Our Commitment to Your Privacy</h4>
                        <p>
                            PawHaven ("we", "our", or "the Platform") operates to connect animal shelters and rescue organizations with conscientious pet adopters. We respect your personal privacy and handle your information with strict care and confidentiality. We do not monetize personal data or sell adopter profiles to commercial marketing networks.
                        </p>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">2. Information We Collect</h4>
                        <p>To facilitate responsible pet adoption matches, we collect the following categories of information:</p>
                        <ul class="d-flex flex-column gap-2 mb-3">
                            <li><strong>Account Credentials:</strong> Name, email address, password hash, and contact phone number.</li>
                            <li><strong>Adoption Application Details:</strong> Housing conditions (ownership vs. rental status, yard fencing), household composition (number of adults/children), existing pets, pet care experience, and daily schedules.</li>
                            <li><strong>Shelter &amp; Listing Records:</strong> Organization licensing status, rescue shelter location, animal health details, vaccination records, and intake documentation.</li>
                            <li><strong>Platform Communications:</strong> Messages exchanged between registered adopters and shelter personnel regarding pet inquiries.</li>
                        </ul>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">3. How Your Information Is Used</h4>
                        <p>We use collected data solely for legitimate adoption and platform administration purposes:</p>
                        <ul class="d-flex flex-column gap-2 mb-3">
                            <li>Evaluating adoption readiness and pet-to-home compatibility.</li>
                            <li>Allowing shelters to contact applicants directly to schedule interviews or meet-and-greets.</li>
                            <li>Maintaining administrative oversight to ensure community safety, verify legitimate shelters, and prevent puppy mill activity.</li>
                            <li>Transmitting status updates regarding application approvals, rejections, or requested clarifications.</li>
                        </ul>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">4. Information Sharing &amp; Disclosure</h4>
                        <p>
                            <strong>Adoption Applications:</strong> When you submit an adoption questionnaire for a pet, the details of that questionnaire are made accessible <em>only</em> to the authorized caretakers and administrators of that specific animal's shelter organization.
                        </p>
                        <p>
                            <strong>No Third-Party Brokers:</strong> We never sell, rent, or trade your personal information or household details to third-party data brokers, pet food marketers, or advertiser networks.
                        </p>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">5. Data Protection &amp; Security</h4>
                        <p>
                            We employ modern industry safeguards including encrypted transmission protocols (HTTPS), parameterized database queries to safeguard against SQL injection, hashed credentials, and role-based access controls to prevent unauthorized access to applicant records.
                        </p>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">6. Your Rights &amp; Preferences</h4>
                        <p>
                            You have the right to access, review, and request updates or deletion of your profile data at any time. If you wish to withdraw an active adoption inquiry or permanently close your PawHaven account, you may do so through your dashboard or by emailing our privacy team.
                        </p>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">7. Contact Regarding Privacy</h4>
                        <p class="mb-0">
                            For inquiries concerning this policy or your personal data, contact us directly at <a href="mailto:privacy@pawhaven.org" class="text-success fw-bold text-decoration-none">privacy@pawhaven.org</a>.
                        </p>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
