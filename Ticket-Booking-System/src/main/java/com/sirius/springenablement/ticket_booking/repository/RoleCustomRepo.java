package com.sirius.springenablement.ticket_booking.repository;
import com.sirius.springenablement.ticket_booking.entity.Roles;
import jakarta.websocket.Session;
import com.sirius.springenablement.ticket_booking.entity.Users;
//import org.hibernate.query.NativeQuery;
//@org.springframework.stereotype.Repository
//public class RoleCustomRepo {
//    @jakarta.persistence.PersistenceContext
//    private jakarta.persistence.EntityManager entityManager;
//
//
//    public java.util.List<Roles> getRoles(Users user){
//       StringBuilder sql=new StringBuilder()
//               .append("SELECT r.name as name\n" +
//                       "FROM users u\n" +
//                       "JOIN user_roles ur ON u.id = ur.user_id\n" +
//                       "JOIN roles r ON r.id = ur.role_id");
//       sql.append("Where 1=1");
//       if(user.getEmail()!=null){
//           sql.append(" and email=:email");
//       }
//        NativeQuery<Roles>  query=((Session)entityManager.getDelegate()).cre
//    }
//}
