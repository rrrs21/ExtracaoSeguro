import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;


/**
 * Conexao
 */
public class Conexao {

    private String url;
    private Connection con;
    private Properties props;

    public Connection getCon() {
        return con;
    }
    Conexao (){
        url = "jdbc:postgresql://172.19.154.244:5432/iglobal2";

        props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "masterkey");
//        props.setProperty("ssl", "true");
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, props);
            System.out.println("conectado");               
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não localizado");
        } catch (SQLException ex){
            System.out.println("Falha ao conectar com o banco de dados");
            ex.printStackTrace();
        }
    }
    public static void consultar (Connection con, String sql, String caminho, List<String> lista, String tipo) throws SQLException {
        //List<Seguro> listaSeguro = new ArrayList<Seguro>();
        HashMap<String, Seguro> registro = new HashMap<>();
        
        try (Statement stmt = con.createStatement()) {
          ResultSet rs = stmt.executeQuery(sql);
         
          while (rs.next()) {
            Seguro seguro = new Seguro();
            seguro.setEmbalagem(rs.getString("Embalagens"));
            seguro.setResumoMercadoria(rs.getString("Mercadoria"));
            if (tipo == "imp"){
                seguro.setNrDi(rs.getString("DI"));
                seguro.setNomeVeiculo(rs.getString("Nome Veiculo"));
                seguro.setCidadeOrigem(rs.getString("Complemento"));
                seguro.setEstadoDestino(rs.getString("Estado"));
                seguro.setCidadeDestino(rs.getString("Cidade"));
            }
            else{
                seguro.setNrDi(rs.getString("DUE"));
                seguro.setNomeVeiculo(rs.getString("Veículo Transportador"));
                seguro.setCidadeOrigem(rs.getString("Cidade Origem"));
                seguro.setEstadoOrigem(rs.getString("Estado Origem"));
                if (rs.getString("Mercadoria").contains("FONTE IRIDIO")){
                    seguro.setEmbalagem("TAMBOR DE PLASTICO");
                }
            }            
            
            seguro.setTipoTransporte(rs.getString("Tipo Transporte"));           
            seguro.setPaisOrigem(rs.getString("Origem"));
            seguro.setPaisDestino(rs.getString("Destino"));
            seguro.setDataSaida(rs.getDate("Saída"));
            seguro.setMoedaFob(rs.getString("Moeda FOB"));
            seguro.setValorFob(rs.getFloat("FOB"));
            if (rs.getString("Moeda Frete")!=null){
                seguro.setMoedaFrete(rs.getString("Moeda Frete"));
            } else {
                seguro.setMoedaFrete(rs.getString("Moeda FOB"));
            }
            seguro.setValorFrete(rs.getFloat("Frete"));
            seguro.setMoedaDespesa(rs.getString("Moeda Despesa"));
            seguro.setValorDespesa(rs.getFloat("Despesa"));
            seguro.setMoedaLucro(rs.getString("Moeda Lucro"));
            seguro.setValorLucro(rs.getFloat("Lucro"));
            seguro.setMoedaImposto(rs.getString("Moeda Impostos"));
            seguro.setValorImposto(rs.getFloat("Impostos"));
            seguro.setObservacao(rs.getString("Observação"));
 //           listaSeguro.add(seguro);
  //         imprimir(seguro);
            if (registro.get(seguro.getObservacao())==null) { 
                registro.put(seguro.getObservacao(), seguro);
            }
            
          }
        } catch (SQLException e) {
          System.out.println("Não foi possivel concluir a pesquisa. Confira a consulta informada.");
          e.printStackTrace();
        } finally {
            
            escrever(caminho, registro, lista, tipo);
        }
      }

    public static void imprimir(Seguro seg) {      
        System.out.println    ("DI: " + seg.getNrDi());
        System.out.println    ("Tipo Transporte: " + seg.getTipoTransporte());
        System.out.println    ("Nome Veiculo: "+ seg.getNomeVeiculo());
        System.out.println    ("Origem: "+seg.getPaisOrigem());
        System.out.println    ("Complemento: "+seg.getCidadeOrigem());
        System.out.println    ("Destino: "+seg.getPaisDestino());
        System.out.println    ("Estado: "+seg.getEstadoDestino());
        System.out.println    ("Cidade: "+seg.getCidadeDestino());
        System.out.println    ("Saída: "+seg.getDataSaida());
        System.out.println    ("Embalagens: "+seg.getEmbalagem());
        System.out.println    ("Mercadoria: "+seg.getResumoMercadoria());
        System.out.println    ("Moeda FOB: "+seg.getMoedaFob());
        System.out.println    ("FOB: "+seg.getValorFob());
        System.out.println    ("Moeda Frete: "+seg.getMoedaFrete());
        System.out.println    ("Frete: "+seg.getValorFrete());
        System.out.println    ("Moeda Despesa: "+seg.getMoedaDespesa());
        System.out.println    ("Despesa: "+seg.getValorDespesa());
        System.out.println    ("Moeda Lucro: "+seg.getMoedaLucro());
        System.out.println    ("Lucro: "+seg.getValorLucro());
        System.out.println    ("Moeda Impostos: "+seg.getMoedaImposto());
        System.out.println    ("Impostos: "+seg.getValorImposto());
        System.out.println    ("Observação: "+seg.getObservacao());
    }

    public static float toBr(Float x) {
        String aux = String.valueOf(x);
        float valor = Float.parseFloat(aux.replace(".", ","));
        return valor;

    }

    public static void escrever (String caminho, HashMap<String, Seguro> seguro, List<String> lista, String tipo){
        String chaves;
 //       java.io.File resultado = new java.io.File(caminho+"\\"+tipo+".csv");
 //       SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        
      /*   try {			
			FileWriter fileWriter= new FileWriter(resultado,true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
            if (tipo == "imp") { 
                printWriter.print("DI;");
            } else { 
                printWriter.print("DUE;");
            }
            printWriter.print("Tipo Transporte;");
            if (tipo == "imp") { 
                printWriter.print("Nome Veiculo;");
            } else { 
                printWriter.print("Veículo Transportador;");
            }
            printWriter.print("Origem;");
            if (tipo == "imp") {
                printWriter.print("Complemento;");
            } else {
                printWriter.print("Estado Origem;");
                printWriter.print("Cidade Origem;");
            }
            printWriter.print("Destino;");
            if (tipo == "imp") {
                printWriter.print("Estado;");
                printWriter.print("Cidade;");
            }
            printWriter.print("Saída;");
            printWriter.print("Embalagens;");
            printWriter.print("Mercadoria;");
            printWriter.print("Moeda FOB;");
            printWriter.print("FOB;");
            printWriter.print("Moeda Frete;");
            printWriter.print("Frete;");
            printWriter.print("Moeda Despesa;");
            printWriter.print("Despesa;");
            printWriter.print("Moeda Lucro;");
            printWriter.print("Lucro;");
            printWriter.print("Moeda Impostos;");
            printWriter.print("Impostos;");
            printWriter.println("Observação");
            for (int i = 0; i < lista.size(); i++) {     
                chaves= lista.get(i);
                printWriter.print(seguro.get(chaves).getNrDi()+";");
                printWriter.print(seguro.get(chaves).getTipoTransporte()+";");
                printWriter.print(seguro.get(chaves).getNomeVeiculo()+";");
                printWriter.print(seguro.get(chaves).getPaisOrigem()+";");
                if (tipo=="exp"){
                    printWriter.print(seguro.get(chaves).getEstadoOrigem()+";");
                }
                printWriter.print(seguro.get(chaves).getCidadeOrigem()+";");
                printWriter.print(seguro.get(chaves).getPaisDestino()+";");
                if (tipo=="imp") {                
                    printWriter.print(seguro.get(chaves).getEstadoDestino()+";");
                    printWriter.print(seguro.get(chaves).getCidadeDestino()+";");
                }
                printWriter.print(seguro.get(chaves).getDataSaida()+";");
                printWriter.print(seguro.get(chaves).getEmbalagem()+";");
                printWriter.print(seguro.get(chaves).getResumoMercadoria()+";");
                printWriter.print(seguro.get(chaves).getMoedaFob()+";");
                printWriter.print(toBr(seguro.get(chaves).getValorFob())+";");
                printWriter.print(seguro.get(chaves).getMoedaFrete()+";");
                printWriter.print(toBr(seguro.get(chaves).getValorFrete())+";");
                printWriter.print(seguro.get(chaves).getMoedaDespesa()+";");
                printWriter.print(toBr(seguro.get(chaves).getValorDespesa())+";");
                printWriter.print(seguro.get(chaves).getMoedaLucro()+";");
                printWriter.print(toBr(seguro.get(chaves).getValorLucro())+";");
                printWriter.print(seguro.get(chaves).getMoedaImposto()+";");
                printWriter.print(toBr(seguro.get(chaves).getValorImposto())+";");
                printWriter.println(seguro.get(chaves).getObservacao());                
            }
            printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			System.out.println("erro"+e.toBr());
		}	*/            
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Seguro");
//            Iterator<Row> rowIterator = sheet.iterator();
        int rownum = 0;
        int cellnum = 0;
        Cell cell;
        Row row;
        HSSFDataFormat numberFormat = workbook.createDataFormat();

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

        HSSFCellStyle dateStyle = workbook.createCellStyle();

        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd-mm-yyyy"));
 
        CellStyle textStyle = workbook.createCellStyle();

        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));

        // Configurando Header
        row = sheet.createRow(rownum++);
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); 
        if (tipo == "imp") {cell.setCellValue("DI");} else {cell.setCellValue("DUE");}
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Tipo Transporte");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); 
        if (tipo == "imp") {cell.setCellValue("Nome Veiculo"); } else {cell.setCellValue("Veiculo transportador");}
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Origem");
        if (tipo == "exp") {cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Estado Origem");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Cidade de origem");} else {
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Complemento");}
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Destino");
        if (tipo == "imp") {
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Estado");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Cidade");}
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Saida");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Embalagens");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Mercadoria");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Moeda Fob");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Fob");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Moeda Frete");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Frete");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Moeda Despesa");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Despesa");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Moeda Lucro");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Lucro");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Moeda Impostos");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Impostos");
        cell = row.createCell(cellnum++); cell.setCellStyle(headerStyle); cell.setCellValue("Observacao");
        for (int i = 0; i < lista.size(); i++) { 
            chaves= lista.get(i);
            row = sheet.createRow(rownum++);
            cellnum = 0;
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getNrDi());
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getTipoTransporte());
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getNomeVeiculo());
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getPaisOrigem());
            if (tipo == "exp") {
                cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getEstadoOrigem());
            }
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getCidadeOrigem());
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getPaisDestino());
            if (tipo == "imp") { 
                cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getEstadoDestino());
                cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getCidadeDestino());
            }
            cell = row.createCell(cellnum++); cell.setCellStyle(dateStyle); cell.setCellValue(DateUtil.getExcelDate(seguro.get(chaves).getDataSaida())); //cell.setCellValue(seguro.get(chaves).getDataSaida()); 
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getEmbalagem());
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getResumoMercadoria());
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getMoedaFob());
            cell = row.createCell(cellnum++); cell.setCellStyle(numberStyle); cell.setCellValue(seguro.get(chaves).getValorFob()); 
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getMoedaFrete());
            cell = row.createCell(cellnum++); cell.setCellStyle(numberStyle); cell.setCellValue(seguro.get(chaves).getValorFrete());
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getMoedaDespesa());
            cell = row.createCell(cellnum++); cell.setCellStyle(numberStyle); cell.setCellValue(seguro.get(chaves).getValorDespesa());
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getMoedaLucro());
            cell = row.createCell(cellnum++); cell.setCellStyle(numberStyle); cell.setCellValue(seguro.get(chaves).getValorLucro());
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getMoedaImposto());
            cell = row.createCell(cellnum++); cell.setCellStyle(numberStyle); cell.setCellValue(seguro.get(chaves).getValorImposto());
            cell = row.createCell(cellnum++); cell.setCellStyle(textStyle); cell.setCellValue(seguro.get(chaves).getObservacao());           
        }
            
            try {
            
            //Escrevendo o arquivo em disco
                FileOutputStream arquivo = new FileOutputStream(new File(caminho+"\\"+tipo+".xls"));
                workbook.write(arquivo);
                arquivo.close();
                workbook.close();               
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
               
    
	}
}

