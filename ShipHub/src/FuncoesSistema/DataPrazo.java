package FuncoesSistema;

public class DataPrazo {
    public static String alterarDataPrazo(String ano,String mes,int dia){
        String diaString=Integer.toString(dia);
        if (dia >31 && (mes.equals("01")||mes.equals("1"))){
            
            dia = dia -31;
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia); 
            mes = "02";
            return String.join("/", ano,mes,diaString);
        }
        else if (dia >28 && (mes.equals("02")||mes.equals("2"))){
            dia = dia -28;
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);

            mes = "03";
            return String.join("/", ano,mes,diaString);
        }
        
        else if (dia >31 && (mes.equals("03")||mes.equals("3"))){
            dia = dia - 31;
            
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);
                
            mes = "04";
            return String.join("/", ano,mes,diaString);
        }

        else if (dia >30 && (mes.equals("04")||mes.equals("4"))){
            dia = dia - 30;
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);
                
            mes = "05";
            return String.join("/", ano,mes,diaString);
        }

        else if (dia >31 && (mes.equals("05")||mes.equals("5"))){
            dia = dia - 31;
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);
                
            mes = "06";
            return String.join("/", ano,mes,diaString);
        }

        else if (dia >30 && (mes.equals("06")||mes.equals("6"))){
            dia = dia - 30;
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);
                
            mes = "07";
            return String.join("/", ano,mes,diaString);
        }

        else if (dia >31 &&(mes.equals("07")||mes.equals("7"))){
            dia = dia - 31;
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);
                
            mes = "08";
            return String.join("/", ano,mes,diaString);
        }

        else if (dia >30 && (mes.equals("08")||mes.equals("8"))){
            dia = dia - 30;
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);
                
            mes = "09";
            return String.join("/", ano,mes,diaString);
        }

        else if (dia >31 && (mes.equals("09")||mes.equals("9"))){
            dia = dia - 31;
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);
                
            mes = "10";
            return String.join("/", ano,mes,diaString);
        }

        else if (dia >30 && mes.equals("10")){
            dia = dia - 30;
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);
                
            mes = "11";
            return String.join("/", ano,mes,diaString);
        }
        else if (dia >31 && mes.equals("11")){
            dia = dia - 31;
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);
                
            mes = "12";
            return String.join("/", ano,mes,diaString);
        }

        else if (dia >30 && mes.equals("12")){
            dia = dia - 30;
            
            if (dia<=9)
                diaString = "0"+Integer.toString(dia);
            else
                diaString = Integer.toString(dia);
                
            mes = "01";

            ano = Integer.toString(Integer.parseInt(ano) + 1);
            return String.join("/", ano,mes,diaString);
        }
        int mes2 = Integer.parseInt(mes);
        if (mes2<=9){
            mes = "0"+String.valueOf(mes2);
            if (dia<=9){
                diaString = "0"+String.valueOf(dia);
            }
            return String.join("/", ano,mes,diaString);    
        }
        else {
            return String.join("/", ano,mes,diaString);
        }
        
    }
}
