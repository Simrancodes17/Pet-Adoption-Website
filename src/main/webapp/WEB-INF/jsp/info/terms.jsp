<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Terms of Service | PawHaven" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
<jsp:include page="/WEB-INF/jsp/common/navbar.jsp" />
<jsp:include page="/WEB-INF/jsp/common/alerts.jsp" />

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-9">
            <!-- Header -->
            <div class="mb-5 pb-3 border-bottom">
                <span class="text-uppercase fw-bold text-success small" style="letter-spacing: 0.05em;">Community Standards</span>
                <h1 class="fw-bold mt-1 mb-2">Terms of Service</h1>
                <p class="text-muted small mb-0">Effective Date: October 2026 | Last Updated: October 2026</p>
            </div>

            <div class="card card-paw p-4 p-md-5 border mb-5">
                <div class="d-flex flex-column gap-4 text-secondary" style="line-height: 1.7;">
                    <section>
                        <h4 class="fw-bold text-dark mb-3">1. Agreement to Terms</h4>
                        <p>
                            By creating an account, browsing listings, submitting an adoption application, or listing an animal on PawHaven ("the Platform"), you agree to be bound by these Terms of Service. If you do not agree with these terms, please do not use the Platform.
                        </p>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">2. Nature of Platform Services</h4>
                        <p>
                            PawHaven operates as an ethical connection platform bridging nonprofit shelters, municipal animal centers, verified foster networks, and prospective pet adopters. PawHaven does not own, house, or act as the legal guardian of the animals listed. All final adoption contracts, custody transfers, and home inspections remain governed directly between the respective shelter organization and the adopter.
                        </p>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">3. Shelter Standards &amp; Prohibitions</h4>
                        <p>Participating shelters and rescue organizations agree that:</p>
                        <ul class="d-flex flex-column gap-2 mb-3">
                            <li>All animals listed are legally under the shelter's care or foster oversight.</li>
                            <li>Profiles accurately reflect known medical issues, vaccination records, behavioral assessments, and special care needs.</li>
                            <li><strong>Commercial Breeding Prohibited:</strong> Commercial pet breeding, puppy mills, backyard breeding, or sales for profit are strictly forbidden on PawHaven. Any accounts violating this principle will be immediately terminated.</li>
                        </ul>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">4. Adopter Commitments</h4>
                        <p>Prospective adopters agree that:</p>
                        <ul class="d-flex flex-column gap-2 mb-3">
                            <li>All information provided in adoption applications, housing situations, and pet experience is complete and truthful.</li>
                            <li>Animals adopted through partner shelters are intended to be cared for as companion animals in humane environments, complying with all local animal welfare laws and municipal licensing requirements.</li>
                            <li>Submitting an adoption application does not constitute a guaranteed right or reservation of any animal until the shelter formally approves and executes an adoption agreement.</li>
                        </ul>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">5. Adoption Fees &amp; Financials</h4>
                        <p>
                            PawHaven charges zero transaction commissions or finder fees to adopters. Any adoption fees requested by individual shelters serve solely to offset veterinary costs, spay/neuter surgeries, microchipping, and care expenses incurred by that organization. Such fees are handled directly between the adopter and the shelter.
                        </p>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">6. User Conduct &amp; Account Responsibility</h4>
                        <p>
                            Users are responsible for safeguarding their login credentials. Any deceptive behavior, harassment, impersonation of rescue organizations, or submission of fraudulent applications will result in account suspension and may be reported to appropriate animal welfare authorities.
                        </p>
                    </section>

                    <section>
                        <h4 class="fw-bold text-dark mb-3">7. Contact Information</h4>
                        <p class="mb-0">
                            For inquiries regarding our terms, shelter listings, or community standards, please reach out to <a href="mailto:terms@pawhaven.org" class="text-success fw-bold text-decoration-none">terms@pawhaven.org</a>.
                        </p>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
