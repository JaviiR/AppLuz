package com.mycompany.cuentaluz.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.cuentaluz.Entidades.Luz;

public class Conexion {
    final String url = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true";
    final String usu = "sa";
    final String pass = "Samuray7)";
    final String bdName = "BdLuz";
    public static final int ESTADO_CANCELADO=1,ESTADO_NO_CANCELADO=0;

    // objecto conexion
    Connection connection = null;
    Statement statement = null;

    public Conexion() {
        Conectar();
        CrearTabla("Luz");
    }

    public boolean Conectar() {
        try {
            connection = DriverManager.getConnection(url, usu, pass);
            System.out.println("Conexion exitosa");
            // verificar si la base de datos ya existe
            statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery("select name from sys.databases where name='" + bdName + "'");
            if (resultado.next()) {
                System.out.println("la DB ya existia");
            } else {
                String StringCreateDB = "CREATE DATABASE " + bdName;
                statement.executeUpdate(StringCreateDB);
                System.out.println("Base de datos rceada exitosamente");
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return true;
    }

    public void CrearTabla(String tabla) {
        ResultSet validarTabla = null;
        boolean existe = true;
        try {
            statement.executeUpdate("USE BdLuz;");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        try {
            validarTabla = statement.executeQuery("select*from " + tabla + ";");
            System.out.println("la tabla ya existe");
        } catch (Exception e) {
            System.out.println("la tabla no existe");
            existe = false;

        }

        try {
            if (!existe) {
                System.out.println("creando tabla....");
                statement.executeUpdate(
                        "create table " + tabla + "(dia int, mes int, año int, principal int,secundario int, total decimal(5,2), estado int);");
                System.out.println("tabla creada");
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }

    public List<Luz> listaDatos() {
        List<Luz> lista = new ArrayList<>();
        try {
            ResultSet resultado = statement.executeQuery("select*from Luz");
            while (resultado.next()) {
                Luz luz = new Luz();
                luz.setDia(resultado.getInt("dia"));
                luz.setMes(resultado.getInt("mes"));
                luz.setAño(resultado.getInt("año"));
                luz.setPrincipal(resultado.getInt("principal"));
                luz.setSecundario(resultado.getInt("secundario"));
                luz.setTotal(resultado.getDouble("total"));
                luz.setEstado(resultado.getInt("estado"));
                lista.add(luz);
            }
        } catch (Exception e) {
            System.out.println("ERROR(listaDatos):"+e.getMessage());
        }

        return lista;
    }

    public int MedicionAnterior(String fecha) {
        int medicionAnterior = 0;
        int mes = Integer.parseInt(fecha.substring(3, 5));
        int año = Integer.parseInt(fecha.substring(6, 10));
        try {
            if (mes == 01) {
                año--;
                System.out.println("mes=" + mes);
                ResultSet resultado = connection
                        .prepareStatement("select medicion from Luz where mes=" + 11 + " and año=" + año)
                        .executeQuery();
                while (resultado.next()) {
                    medicionAnterior = resultado.getInt("secundario");
                }

            } else if (mes == 02) {
                año--;
                System.out.println("mes=" + mes);
                ResultSet resultado = connection
                        .prepareStatement("select medicion from Luz where mes=" + 12 + " and año=" + año)
                        .executeQuery();
                while (resultado.next()) {
                    medicionAnterior = resultado.getInt("secundario");
                }
            } else {
                mes -= 2;
                System.out.println("mes=" + mes);
                System.out.println("año=" + año);
                ResultSet resultado = connection
                        .prepareStatement("select secundario from Luz where mes=" + mes + " and año=" + año)
                        .executeQuery();
                while (resultado.next()) {
                    medicionAnterior = Integer.parseInt(resultado.getString("secundario"));
                    System.out.println("medicion anterior: " + medicionAnterior);
                }

            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return medicionAnterior;
    }

    public boolean IngresarDatos(String fecha, int principal,int secundario, double total) {
        int dia = Integer.parseInt(fecha.substring(0, 2));
        int mes = Integer.parseInt(fecha.substring(3, 5));
        int año = Integer.parseInt(fecha.substring(6, 10));

        try {
            boolean validar = false;
            ResultSet resultado = statement.executeQuery("select*from Luz");
            while (resultado.next()) {
                
                int mesBD = Integer.parseInt(resultado.getString("mes"));
                int añoBD = Integer.parseInt(resultado.getString("año"));
                if(mes!=01){
                    if (mesBD == mes-1 && añoBD == año) {
                    validar = true;
                }
                }else{
                    if (mesBD == 12 && añoBD == año-1) {
                    validar = true;
                }
                }
                
            }
            if (!validar) {
                if (mes == 01) {
                    año--;
                    statement.executeUpdate(
                            "insert into " + "Luz" + " values(" + dia + "," + 12 + "," + año + "," + principal + ","+secundario+","
                                    + total +", 0"
                                    + ");");
                } else {
                    mes--;
                    statement.executeUpdate(
                            "insert into " + "Luz" + " values(" + dia + "," + mes + "," + año + "," + principal + ","+secundario+","
                                    + total +", 0"
                                    + ");");
                }

                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("no se pudo ingresar los datos");
            return false;
        }
    }
    
    public void EliminarRegistro(int mes,int año){
        try {
        statement.executeUpdate("delete from Luz where mes='"+mes+"' and año='"+año+"'");
            System.out.println("Registro eliminado");
        } catch (Exception e) {
            System.out.println("El registro no se pudo eliminar");
            System.out.println("ERROR: "+e.getMessage());
                    
        }
        
    }
    /**
     * 
     * @param estado ingresar alguno de los estados staticos de esta clase {@code ESTADO_CANCELADO} o {@code ESTADO_NO_CANCELADO}
     */
    public void CambiarEstado(int estado,int mes,int año){
        try {
            statement.executeUpdate("UPDATE luz set estado="+estado+" where mes="+mes+" and año="+año);
        } catch (Exception e) {
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    public boolean desconectar() {

        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar la conexion: " + e.getMessage());
        }

        return true;
    }

    

}
