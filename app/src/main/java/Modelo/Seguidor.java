package Modelo;

public class Seguidor {

    private String id_registro;
    private boolean estado;
    private String seguido_id;
    private String seguidor_id;


    public String getId_registro() {
        return id_registro;
    }

    public void setId_registro(String id_registro) {
        this.id_registro = id_registro;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getSeguido_id() {
        return seguido_id;
    }

    public void setSeguido_id(String seguido_id) {
        this.seguido_id = seguido_id;
    }

    public String getSeguidor_id() {
        return seguidor_id;
    }

    public void setSeguidor_id(String seguidor_id) {
        this.seguidor_id = seguidor_id;
    }
}
