package com.deliver.EpamTest;

public class Estudiante extends Persona implements EstudianteEpam{
    private long idEstudiante;
    public static final String FILTRO_SIMPLE = "simple";
    public static final String FILTRO_MEDIO = "medio";
    public static final String FILTRO_COMPLETO = "copleto";

    public long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    @Override
    public void imprimirDatos(){
        System.out.println(" idEstudiante:" + this.idEstudiante +"nombe: "+ this.name + " className :"+this.lastName +
                "age :"+this.age);
    }

    public void imprimirDatos(String formato){
        switch (formato){
            case FILTRO_SIMPLE:
                System.out.println("nombre: "+ this.name + " lastName :"+this.lastName);
                break;
            case FILTRO_MEDIO:
                System.out.println("idEstudiante:" + this.idEstudiante +"nombe: "+ this.name + " className :"+this.lastName);
                break;
            case FILTRO_COMPLETO:
                System.out.println(" idEstudiante:" + this.idEstudiante +"nombe: "+ this.name + " className :"+this.lastName +
                        "age :"+this.age+" newVal:"+formato);
                break;
        }
    }

    public String getData(String formato){
        String data = "";
        switch (formato){
            case FILTRO_SIMPLE:
                data = "nombre: "+ this.name + " lastName :"+this.lastName;
                System.out.println("nombre: "+ this.name + " lastName :"+this.lastName);
                break;
            case FILTRO_MEDIO:
                data = "idEstudiante:" + this.idEstudiante +"nombe: "+ this.name + " className :"+this.lastName;
                System.out.println("idEstudiante:" + this.idEstudiante +"nombe: "+ this.name + " className :"+this.lastName);
                break;
            case FILTRO_COMPLETO:
                data = " idEstudiante:" + this.idEstudiante +"nombe: "+ this.name + " className :"+this.lastName +
                        "age :"+this.age+" newVal:"+formato;
                System.out.println(" idEstudiante:" + this.idEstudiante +"nombe: "+ this.name + " className :"+this.lastName +
                        "age :"+this.age+" newVal:"+formato);
                break;
        }

        return data;
    }

}
