package FuncoesSistema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.time.temporal.ChronoUnit;

public class GerarData {

    private LocalDateTime dataDeInscricao = LocalDateTime.now();

    public LocalDateTime getDataDeInscricao() {
        return dataDeInscricao;
    }

    public static String getDataSaida(){
        GerarData ob =  new GerarData();
        String data = String.valueOf(ob.getDataDeInscricao().getYear())+"/"+String.valueOf(ob.getDataDeInscricao().getMonthValue())+"/"+String.valueOf(ob.getDataDeInscricao().getDayOfMonth());
        String dataFinal = DataPrazo.alterarDataPrazo(data.split("/")[0], data.split("/")[1], Integer.parseInt(data.split("/")[2]));
        return dataFinal;
    }

    public static String getDataChegada(String data){
        int dia = Integer.parseInt(data.split("/")[2]) + 20;
        String nData = DataPrazo.alterarDataPrazo(data.split("/")[0], data.split("/")[1], dia);
        return nData;
    }

    public static String getDataPagamento(String data1,String data2){
        Random rand = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate1 = LocalDate.parse(data1, formatter);
        LocalDate localDate2 = LocalDate.parse(data2, formatter);
        
        int diferencaDias = GerarData.calcularDiferencaDias(localDate1, localDate2);
        int dia = Integer.parseInt(data1.split("/")[2]);
        
        int diaPag = rand.nextInt(dia,diferencaDias);
        
        String dataPagagamento = DataPrazo.alterarDataPrazo(data1.split("/")[0], data1.split("/")[1], diaPag);
        return dataPagagamento;
    }

    public static int calcularDiferencaDias(LocalDate data1, LocalDate data2) {
        return (int) ChronoUnit.DAYS.between(data1, data2);
    }


    public static String gerarDataPrazo(String data){
        int diaPrazo = Integer.parseInt(data.split("/")[2]) + 7; 
        String dataPrazo = DataPrazo.alterarDataPrazo(data.split("/")[0], data.split("/")[1], diaPrazo);
        return dataPrazo;
    }

    public static void main(String[] args) {
        System.out.println(GerarData.gerarDataPrazo(getDataSaida()));
    }
}
