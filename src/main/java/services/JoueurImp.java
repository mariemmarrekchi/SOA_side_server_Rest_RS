package services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Consumes( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//spécifier le type de représentation de la valeur de retour
@Produces( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("/joueur")
public class JoueurImp implements InterfaceJoeur{

    private final static String URL = "jdbc:mysql://localhost:3306/soa";

    /**
     * Nom du user
     */
    private final static String LOGIN = "root";

    /**
     * Mot de passe du user
     */
    private final static String PASSWORD = "";

    private Connection getConnexion() throws SQLException {
        final Connection con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        return con;
    }
    @POST
    @Path("/ajout")

    public Joueur addJoueur(Joueur p) {
        Connection con = null;
        PreparedStatement ps = null;
        Statement stmt =null;
        Joueur np =null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" insert into joueur (id, nom,id_eq,prenom,age)  values (0,?,?,?,?)");
            // passer les paramètres (nom et age) ( la colonne id est auto incrémentable)
            ps.setString(1, p.getNom());
            ps.setInt(2, p.getId_eq());
            ps.setString(3, p.getPrenom());
            ps.setInt(4, p.getAge());
            // executer la requête insert
            ps.executeUpdate();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `joueur` WHERE id =( select max(id) from joueur)");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                int id_eq = rs.getInt("id_eq");
                String prenom=rs.getString("prenom");
                int age=rs.getInt("age");
                np = new Joueur(id,nom,id_eq,prenom,age);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    // Le ps.close ferme automatiquement le resultSet
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return np;
    }

    @Override
    @GET
    @Path("/getAll")
    public List<Joueur> listJoueur() {
        List<Joueur> pers = new ArrayList<Joueur>();

        Connection con = null;
        Statement stmt = null;

        try {
            con = getConnexion();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from joueur ");
//ResultSet r=stmt.executeQuery("select nom from joueur,equipe where joueur.id_eq=equipe.id;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
              int id_eq=rs.getInt("id_eq");
                String prenom=rs.getString("prenom");
                int age=rs.getInt("age");

                Joueur p = new Joueur(id,nom,id_eq,prenom,age);
                pers.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (stmt != null) {
                try {
                    // Le stmt.close ferme automatiquement le resultSet
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }return pers;


    }

    @PUT
    @Path("/up")
    public Reponse updateJoueur(Joueur p) {

        Reponse reponse = new Reponse();

        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" update joueur set nom= ? , id_eq=? , prenom=? , age=? where id=?");
            // passer les paramètres (nom et age) ( la colonne id est auto incrémentable)
            ps.setString(1, p.getNom());
            ps.setInt(2, p.getId_eq());
            ps.setString(3, p.getPrenom());
            ps.setInt(4, p.getAge());
            ps.setInt(5, p.getId());
            // executer la requête
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    // Le ps.close ferme automatiquement le resultSet
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        reponse.setStatus(true);
        reponse.setMessage("Joueur mise à jour avec succès...");
        return reponse;

    }
    @GET
    @Path("/{id}/get")
    public Joueur getJoueurById(@PathParam("id") int id) {
        Joueur t=null;
        try {
            Connection cnx= getConnexion();
            PreparedStatement ps=cnx.prepareStatement("select * from joueur where id=? ");
            ps.setInt(1,id);

            ResultSet rs=ps.executeQuery();
            if(rs!=null)
            {
                while(rs.next())
                {
                    t=new Joueur(rs.getInt("id"),rs.getString("nom"),rs.getInt("id_eq"),rs.getString("prenom"),rs.getInt("age"));
                }
            }
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return t;

    }
    @Override
    @DELETE
    @Path("/{id}/delete")
    public Reponse deleteJoueur(@PathParam("id") int id) {
        Reponse reponse = new Reponse();
        try {
            Joueur tour=getJoueurById(id);
            if (tour == null) {
                reponse.setStatus(false);
                reponse.setMessage("Joueur non existante...");
                return reponse;
            }
            Connection cnx=getConnexion();
            PreparedStatement ps = cnx.prepareStatement("delete from joueur where id= ?");
            ps.setInt(1,id);
            ps.executeUpdate();
            reponse.setMessage("Joueur supprimé avec succès...");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            reponse.setMessage("Error :( ");

        }
        return reponse;
    }

    @DELETE
    @Path("/delete")
    public void deleteJ(Joueur j) {
        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" delete from joueur  where id=? ");
            // passer le paramètre id
            ps.setInt(1, j.getId());
            // executer la requête
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (ps != null) {
                try {
                    // Le ps.close ferme automatiquement le resultSet
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
