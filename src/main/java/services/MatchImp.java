package services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Consumes( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//spécifier le type de représentation de la valeur de retour
@Produces( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("/match")
public class MatchImp implements InterfaceMatch{

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
    @Override
    @POST
    @Path("/ajout")
    public Match addMatch(Match m) {
        Connection con = null;
        PreparedStatement ps = null;
        Statement stmt =null;
        Match np =null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" insert into mat (id, libelle, date,id_tour)  values (0,?,?,?)");
            // passer les paramètres (nom et age) ( la colonne id est auto incrémentable)
            ps.setString(1, m.getLibelle());
            ps.setString(2, m.getDate());
            ps.setInt(3, m.getId_tour());

            // executer la requête insert
            ps.executeUpdate();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `mat` WHERE id =( select max(id) from mat)");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("libelle");
                String date = rs.getString("date");
                int id_tour = rs.getInt("id-tour");

                np = new Match(id,nom,date,id_tour);
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
    public List<Match> listMatch() {
        List<Match> pers = new ArrayList<Match>();

        Connection con = null;
        Statement stmt = null;

        try {
            con = getConnexion();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM mat order by id");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("libelle");
                String date = rs.getString("date");

                int id_tour = rs.getInt("id_tour");

                Match p = new Match(id,nom,date,id_tour);
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
    @Path("/update")
    public Reponse updateMatch(Match m ){
        Reponse reponse = new Reponse();
        Connection con = null;

        try {
            con = getConnexion();
            // préparer la requête SQL
            PreparedStatement ps = con.prepareStatement("update mat set  libelle = ?,date = ?,id_tour=? where id= ?");
            ps.setString(1, m.getLibelle());
            ps.setString(2, m.getDate());
            ps.setInt(3, m.getId_tour());
            ps.setInt(4, m.getId());
            ps.executeUpdate();
            reponse.setMessage("match modifié avec succès...");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            reponse.setMessage("Error :( ");
        }
        return  reponse;
    }
    @GET
    @Path("/{id}/get")
    public Match getMatchById(@PathParam("id") int id) {
        Match m=null;
        try {
            Connection cnx= getConnexion();
            PreparedStatement ps=cnx.prepareStatement("select * from mat where id=? ");
            ps.setInt(1,id);

            ResultSet rs=ps.executeQuery();
            if(rs!=null)
            {
                while(rs.next())
                {
                    m=new Match(rs.getInt("id"),rs.getString("libelle"),rs.getString("date"),rs.getInt("id_tour"));
                }
            }
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return m;

    }
    @Override
    @DELETE
    @Path("/{id}/delete")
    public void deleteMatch(@PathParam("id") int id) {
        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" delete from mat  where id=? ");
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

    @DELETE
    @Path("/delete")
    public void deleteJ(Match m) {
        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" delete from mat  where id=? ");
            // passer le paramètre id
            ps.setInt(1, m.getId());
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
