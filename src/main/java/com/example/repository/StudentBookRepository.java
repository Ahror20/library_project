package com.example.repository;

import com.example.entity.BookEntity;
import com.example.entity.StudentBookEntity;
import com.example.entity.StudentEntity;
import com.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository
public class StudentBookRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public Boolean takingBook(StudentBookEntity studentBookEntity) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        boolean isSaved = false;
        try {
            transaction = session.beginTransaction();
            session.save(studentBookEntity);
            transaction.commit();
            isSaved = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isSaved;
    }

    public StudentBookEntity getStudentBookById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM StudentBookEntity s WHERE s.id = :id";
            Query query = session.createQuery(hql, StudentBookEntity.class);
            query.setParameter("id", id);

            return (StudentBookEntity) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentBookEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(" from StudentBookEntity ");
        List<StudentBookEntity> studentBookEntities = query.getResultList();
        return studentBookEntities;
    }

    public Boolean returningBook(StudentBookEntity studentBook, Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean isUpdate = false;
        try {
            tx = session.beginTransaction();
            StudentBookEntity entityToUpdate = session.get(StudentBookEntity.class, id);
            if (entityToUpdate != null) {
                entityToUpdate.setStudentId(studentBook.getStudentId());
                entityToUpdate.setBookId(studentBook.getBookId());
                entityToUpdate.setCreatedDate(studentBook.getCreatedDate());
                entityToUpdate.setReturnedDate(studentBook.getReturnedDate());
                entityToUpdate.setStatus(studentBook.getStatus());
                entityToUpdate.setDuration(studentBook.getDuration());
                session.update(entityToUpdate);
                isUpdate = true;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isUpdate;
    }

    public StudentEntity getStudentBookByStudentId(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select s.studentId  FROM StudentBookEntity s WHERE s.studentId.id = :id";
            Query query = session.createQuery(hql, StudentBookEntity.class);
            query.setParameter("id", id);
            List list = query.getResultList();

            return (StudentEntity) list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    public List<BookEntity> getAllBooksForStudent(Integer id) {
//        String jpql = "SELECT sb.bookId FROM StudentBookEntity sb WHERE sb.studentId.id = :studentId";
//        TypedQuery<BookEntity> query = entityManager.createQuery(jpql, BookEntity.class);
//        query.setParameter("studentId", id);
//        return query.getResultList();
//
//
//    }
    @Transactional
    public List<BookEntity> getBooksForStudent(Long studentId) {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);

        Root<StudentBookEntity> studentBookRoot = criteriaQuery.from(StudentBookEntity.class);
        Join<StudentBookEntity, BookEntity> bookJoin = studentBookRoot.join("bookId");

        Predicate condition = criteriaBuilder.equal(studentBookRoot.get("studentId").get("id"), studentId);
        criteriaQuery.select(bookJoin).where(condition);

        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }



}




