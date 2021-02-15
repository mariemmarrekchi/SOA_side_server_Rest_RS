package services;

import javax.ws.rs.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/personne")
public class DBImp {
    /**
     * URL de connection
     */
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
    @GET
    @Path("/getAll")
    // retourner la liste totale des personnes
    public List<Personne> getAllPersonnes() {

        List<Personne> pers = new ArrayList<Personne>();

        Connection con = null;
        Statement stmt = null;

        try {
            con = getConnexion();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM personne");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                int age = rs.getInt("age");

                Personne p = new Personne(id,nom,age);
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
        }

        return pers;
    }
    @POST
    @Path("/aj")
    // ajouter une personne (en se basant sur l'objet reçu en pramètre)
    public Joueur addPersonne(Joueur p) {
        Connection con = null;
        PreparedStatement ps = null;
        Statement stmt =null;
        Joueur np =null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" insert into joueur (id, nom, carte_j,carte_r,nb_bute,id_eq)  values (0,?,?,?,?,?)");
            // passer les paramètres (nom et age) ( la colonne id est auto incrémentable)
            ps.setString(1, p.getNom());

            ps.setInt(5, p.getId_eq());
            // executer la requête insert
            ps.executeUpdate();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `joueur` WHERE id =( select max(id) from joueur)");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                int carte_j = rs.getInt("carte_j");
                int carte_r = rs.getInt("carte_r");
                int nb_bute = rs.getInt("nb_bute");
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
    @PUT
    @Path("/up")
    // mettre à jour une personne (en se basant sur l'objet reçu en pramètre)
    public void updatePersonne(Personne p) {



        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" update personne set nom= ?, age=?  where id=?");
            // passer les paramètres (nom et age) ( la colonne id est auto incrémentable)
            ps.setString(1, p.getNom());
            ps.setInt(2, p.getAge());
            ps.setInt(3, p.getId());
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

    public void deletePersonne(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" delete from personne  where id=? ");
            // passer le paramètre id
            ps.setInt(1, id);
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
    @GET
@Path("/{nom}/{age}/getByNom")
    public Personne getUserByLoginAndPassword(@PathParam("nom") String n, @PathParam("age") String a) throws SQLException {
//récupérer une connexion à la BD
        Connection conn= getConnexion();
        Personne u= null;
        try {
// préparer la requête SQL

            PreparedStatement ps = conn.prepareStatement(" select * from personne where nom =? and age = ?");
            ps.setString(1, n);
            ps.setString(2, a);
            ResultSet rs= ps.executeQuery();
            if (rs!=null)
            {
                while(rs.next())
                {
                    u = new Personne();
                    u.setId(rs.getInt("id"));
                    u.setNom(rs.getString("nom"));
                    u.setAge(rs.getInt("age"));

                }
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }
    public List<Personne> findPersonnesByName(String name) {
        List<Personne> pers = new ArrayList<Personne>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getConnexion();
            stmt = con.prepareStatement("SELECT * FROM personne WHERE nom = ? ");
            stmt.setString(1, name);

            final ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                int age = rs.getInt("age");

                Personne p = new Personne(id,nom,age);
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
        }

        return pers;
    }

}
