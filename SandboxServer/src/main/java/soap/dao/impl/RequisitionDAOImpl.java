package soap.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.dao.RequisitionDAO;
import soap.entity.Requisition;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by EGBoldyr on 19.03.18.
 */

@Repository
@Transactional
public class RequisitionDAOImpl implements RequisitionDAO {

    private Logger log;

    @Autowired
    private SessionFactory factory;

    @PostConstruct
    private void initialize() {
        log = LoggerFactory.getLogger(RequisitionDAOImpl.class);
    }

    @Override
    public Long create(Requisition requisition) {
        try {
            log.info("Creating requisition...");
            Long requisitionId = (Long) factory.getCurrentSession().save(requisition);
            log.info("Creating requisition SUCCESS. New requisition [ID : " + requisitionId + "]");
            return requisitionId;
        } catch (HibernateException exc) {
            factory.getCurrentSession().getTransaction().rollback();
            log.error("Creating requisition FAIL.");
            return null;
        }
    }

    @Override
    public boolean update(Requisition requisition) {
        return false;
    }

    @Override
    public List<Requisition> findAll() {
        try {
            log.info("Receiving all requisitions...");
            List<Requisition> requisitions =
                    factory.getCurrentSession().createCriteria(Requisition.class).list();
            log.info("Receiving all requisitions, COMPLETE.");
            return requisitions;
        } catch (HibernateException exc) {
            log.info("Receiving all requisitions, FAIL.");
            return null;
        }
    }
}
