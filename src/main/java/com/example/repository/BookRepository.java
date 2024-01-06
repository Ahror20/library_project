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
public class BookRepository {
    public Boolean createBook(BookEntity dto) {
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

    public List<BookEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(" from BookEntity ");
        List<BookEntity> bookEntities = query.getResultList();
        return bookEntities;

    }

    public Boolean deleteBookById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean isDelete = false;

        try {
            tx = session.beginTransaction();

            BookEntity bookToDelete = session.get(BookEntity.class, id);

            if (bookToDelete != null) {
                session.delete(bookToDelete); // Delete the entity
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

    public Boolean updateBookById(BookEntity dto, Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean isUpdate = false;
        try {
            tx = session.beginTransaction();
            BookEntity entityToUpdate = session.get(BookEntity.class, id);
            if (entityToUpdate != null) {
                entityToUpdate.setTitle(dto.getTitle());
                entityToUpdate.setAuthor(dto.getAuthor());
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

    public BookEntity getBookByTitleAndAuthor(String title,String author){
       try
           (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM BookEntity b WHERE b.title = :title AND b.author = :author";
            Query query = session.createQuery(hql, BookEntity.class);
            query.setParameter("title", title);
            query.setParameter("author", author);
            return (BookEntity) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public BookEntity getBookById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM BookEntity s WHERE s.id = :id";
            Query query = session.createQuery(hql, BookEntity.class);
            query.setParameter("id", id);

            return (BookEntity) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
