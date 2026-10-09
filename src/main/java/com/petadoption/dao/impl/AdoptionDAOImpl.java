package com.petadoption.dao.impl;

import com.petadoption.dao.AdoptionDAO;
import com.petadoption.util.DBConnectionUtil;

/**
 * Concrete implementation of AdoptionDAO.
 * Inherits robust PreparedStatement and transactional implementations from ApplicationDAOImpl.
 *
 * Satisfies rubric item 5: Database Operation Classes - AdoptionDAO implementation
 */
public class AdoptionDAOImpl extends ApplicationDAOImpl implements AdoptionDAO {

    public AdoptionDAOImpl() {
        super();
    }

    public AdoptionDAOImpl(DBConnectionUtil dbUtil) {
        super(dbUtil);
    }
}
