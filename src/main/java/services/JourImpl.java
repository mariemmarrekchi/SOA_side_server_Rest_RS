package services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Consumes( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
//spécifier le type de représentation de la valeur de retour
@Produces( {MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("/jour")
public class JourImpl implements InterfaceJour{
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
    public Jour addJour(Jour j) {
        Connection con = null;
        PreparedStatement ps = null;
        Statement stmt =null;
        Jour jo =null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" insert into journ (id, date,id_mat,id_eq,nom_joueur, carte_j,carte_r,etat,nb_bute,id_eq2,carte_j2,carte_r2)  values (0,?,?,?,?,?,?,?,?,?,?,?)");
            // passer les paramètres (nom et age) ( la colonne id est auto incrémentable)
            ps.setString(1, j.getDate());
            ps.setInt(2, j.getId_mat());
            ps.setInt(3, j.getId_eq());
            ps.setInt(4, j.getCarte_j());
            ps.setInt(5, j.getCarte_r());
            ps.setString(6, j.getNb_bute());
            ps.setInt(7, j.getId_eq2());
            ps.setInt(8, j.getCarte_j2());
            ps.setInt(9, j.getCarte_r2());
            // executer la requête insert
            ps.executeUpdate();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `journ` WHERE id =( select max(id) from journ)");

            while (rs.next()) {
                int id = rs.getInt("id");
                int id_mat = rs.getInt("id_mat");
                int id_eq = rs.getInt("id_eq");
                String date = rs.getString("date");
                String nom_j = rs.getString("nom_joueur");
                int carte_j = rs.getInt("carte_j");
                int carte_r = rs.getInt("carte_r");
                String etat = rs.getString("etat");
                String nb_bute = rs.getString("nb_bute");
                int id_eq2 = rs.getInt("id_eq2");
                int carte_j2 = rs.getInt("carte_j2");
                int carte_r2 = rs.getInt("carte_r2");
                jo = new Jour(id,date,id_mat,id_eq,carte_j,carte_r,nb_bute,id_eq2,carte_j2,carte_r2);
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
        return jo;
    }

    @Override
    @GET
    @Path("/getAll")
    public List<Jour> listJour() {
        List<Jour> jour = new ArrayList<Jour>();

        Connection con = null;
        Statement stmt = null;

        try {
            con = getConnexion();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from journ order by nb_bute DESC");
//ResultSet r=stmt.executeQuery("select nom from joueur,equipe where joueur.id_eq=equipe.id;");
            while (rs.next()) {
                int id = rs.getInt("id");
                int id_mat = rs.getInt("id_mat");
                int id_eq = rs.getInt("id_eq");
                String date = rs.getString("date");
                int carte_j = rs.getInt("carte_j");
                int carte_r = rs.getInt("carte_r");
                String  nb_bute = rs.getString("nb_bute");
                int id_eq2 = rs.getInt("id_eq2");
                int carte_j2 = rs.getInt("carte_j2");
                int carte_r2 = rs.getInt("carte_r2");
                Jour   j= new Jour(id,date,id_mat,id_eq,carte_j,carte_r,nb_bute,id_eq2,carte_j2,carte_r2);
                jour.add(j);
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
        }return jour;

    }
    @GET
    @Path("/{id}/get")
    @Override
    public Jour getJourById(@PathParam("id")int id) {
        Jour j=null;
        try {
            Connection cnx= getConnexion();
            PreparedStatement ps=cnx.prepareStatement("select * from journ where id=? ");
            ps.setInt(1,id);

            ResultSet rs=ps.executeQuery();
            if(rs!=null)
            {
                while(rs.next())
                {
                    j=new Jour(rs.getInt("id"),rs.getString("date"),rs.getInt("id_mat"),rs.getInt("id_eq"),rs.getInt("carte_j"),rs.getInt("carte_r"),rs.getString("nb_bute"),rs.getInt("id_eq2"),rs.getInt("carte_j2"),rs.getInt("carte_r2"));
                }
            }
            ps.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return j;

    }
    @Path("/update")
    @PUT
    @Override
    public Reponse updateJour(Jour j) {
        Reponse reponse = new Reponse();

        Connection con = null;
        PreparedStatement ps = null;
        //récupérer une connexion à la BD
        try {
            con = getConnexion();
            // préparer la requête SQL
            ps = con.prepareStatement(" update journ set date= ?,id_mat=? ,id_eq=?, carte_j=? , carte_r=?  ,nb_bute=?  ,id_eq2=? , carte_j2=? , carte_r2=? where id=?");
            // passer les paramètres (nom et age) ( la colonne id est auto incrémentable)
            ps.setString(1, j.getDate());
            ps.setInt(2, j.getId_mat());
            ps.setInt(3, j.getId_eq());
            ps.setInt(4, j.getCarte_j());
            ps.setInt(5, j.getCarte_r());
            ps.setString(6, j.getNb_bute());
            ps.setInt(7, j.getId_eq2());
            ps.setInt(8, j.getCarte_j2());
            ps.setInt(9, j.getCarte_r2());
            ps.setInt(10, j.getId());

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
        reponse.setMessage("Jour mise à jour avec succès...");
        return reponse;
    }

    @DELETE
    @Path("/{id}/delete")
    @Override
    public Reponse deleteJour(@PathParam("id") int id) {
        Reponse reponse = new Reponse();
        try {
            Jour j=getJourById(id);
            if (j == null) {
                reponse.setStatus(false);
                reponse.setMessage("Jour non existante...");
                return reponse;
            }
            Connection cnx=getConnexion();
            PreparedStatement ps = cnx.prepareStatement("delete from journ where id= ?");
            ps.setInt(1,id);
            ps.executeUpdate();
            reponse.setMessage("Jour supprimé avec succès...");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            reponse.setMessage("Error :( ");

        }
        return reponse;
    }
}
