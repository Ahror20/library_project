package com.example.repository;

import com.example.entity.BookEntity;
import com.example.entity.StudentEntity;
import com.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class StudentRepository {
    public Boolean saveStudent(StudentEntity dto) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        boolean isSaved = false;
        try {
            transaction = session.beginTransaction();
            session.save(dto);
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

    public List<StudentEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(" from StudentEntity ");
        List<StudentEntity> bookEntities = query.getResultList();
        return bookEntities;
    }
    public StudentEntity getStudentByPhone(String phone){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM StudentEntity s WHERE s.phone = :phone";
            Query query = session.createQuery(hql, StudentEntity.class);
            query.setParameter("phone", phone);

            return (StudentEntity) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public StudentEntity getStudentByID(Integer id){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM StudentEntity s WHERE s.id = :id";
            Query query = session.createQuery(hql, StudentEntity.class);
            query.setParameter("id", id);

            return (StudentEntity) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean deleteStudentById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean isDelete = false;
        try {
            tx = session.beginTransaction();
            StudentEntity student = session.get(StudentEntity.class, id);
            if (student != null) {
                session.delete(student);
                isDelete = true;
                System.out.println("Book deleted successfully");
            } else {
                System.out.println("Book with specified ID not found");
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
        return isDelete;

    }

    public Boolean updateStudent(StudentEntity dto, Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean isUpdate = false;
        try {
            tx = session.beginTransaction();
            StudentEntity entityToUpdate = session.get(StudentEntity.class, id);
            if (entityToUpdate != null) {
                entityToUpdate.setName(dto.getName());
                entityToUpdate.setSurname(dto.getSurname());
                entityToUpdate.setPhone(dto.getPhone());
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
}
