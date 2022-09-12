import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Consulta {
   // List<String> lista = new ArrayList<String>();
    public static void main(String[] args) throws SQLException {
        String importacao = args[0];        
        String exportacao = args[1];
//        String importacao = "'LST221032','LST220866'";        
//        String exportacao = "'BHZ220048'";
        String sqlimp = "SELECT distinct c.nrdeclaracaoimportacao AS \"DI\""+", viatransporte.nmviatransporte AS \"Tipo Transporte\"" + 
        ", case when viatransporte.nmviatransporte = 'Marítima' then embarcacao.nmembarcacao " + 
        "Else (case when p.nrconhecmaster = '' then p.nrconhecimento Else p.nrconhecmaster end ) "+
        "end AS \"Nome Veiculo\", pais.nmpais AS \"Origem\", cidade.nmcidade AS \"Complemento\", 'Brasil' As \"Destino\","+
        "(select uf.nmuf from uf, cidade where p.idcidadedesembarque = cidade.idcidade and cidade.iduf = uf.iduf) AS \"Estado\","+
        "(select nmcidade from cidade where p.idcidadedesembarque = cidade.idcidade) AS \"Cidade\", p.dtembarque as \"Saída\", "+
        "especiesvolumes.nmespeciesvolumes AS \"Embalagens\", p.txresumomercadoria AS \"Mercadoria\", moeda.sigla AS \"Moeda FOB\","+
        "p.vlrmlemneg as \"FOB\", (select sigla from moeda where p.idmoedafrete = moeda.idmoeda) AS \"Moeda Frete\", "+
        "case when p.idpessoacliente = 94 or p.idpessoacliente = 1057 then p.vlrfretecollectmneg Else '0' end as \"Frete\","+
        "moeda.sigla AS \"Moeda Despesa\", case when p.idpessoacliente = 94 or p.idpessoacliente = 1057 then (p.vlrmlemneg + p.vlrfretecollectmneg) * 0.1 "+
        "Else p.vlrmlemneg * 0.1 end AS \"Despesa\", 'BRL' AS \"Moeda Lucro\", '0' AS \"Lucro\", 'BRL' AS \"Moeda Impostos\","+
        "'0' AS \"Impostos\", substring (p.nrprocesso, 4,6) as \"Observação\" FROM public.dicapa c, public.diadicao, public.processo p, "+
        "public.moeda, public.viatransporte, public.cidade, public.pais, public.especiesvolumes, public.processovolumes, public.embarcacao "+
        "WHERE p.idprocesso = c.idprocesso AND p.idprocesso = diadicao.idprocesso AND p.idmoedamle = moeda.idmoeda AND "+
        "(p.idembarcacao is null or p.idembarcacao = embarcacao.idembarcacao) and p.idcidadeembarque = cidade.idcidade AND "+
        "p.idviatransporte = viatransporte.idviatransporte AND processovolumes.idprocesso = p.idprocesso AND "+
        "processovolumes.idespeciesvolumes = especiesvolumes.idespeciesvolumes AND cidade.idpais = pais.idpais AND P.nrprocesso IN ("+
        importacao+")";
        String sqlexp = "SELECT distinct e.nrdue AS \"DUE\", viatransporte.nmviatransporte AS \"Tipo Transporte\","+
        " case when viatransporte.nmviatransporte = 'Marítima' then embarcacao.nmembarcacao Else (case when p.nrconhecmaster = '' then p.nrconhecimento "+
        "Else p.nrconhecmaster end ) end AS \"Veículo Transportador\",'Brasil' As \"Origem\", public.uf.nmuf as \"Estado Origem\", "+
        "public.cidade.nmcidade AS \"Cidade Origem\", pais.nmpais AS \"Destino\", p.dtembarque as \"Saída\", '' AS \"Embalagens\", "+
        " p.txresumomercadoria AS \"Mercadoria\", moeda.sigla AS \"Moeda FOB\", e.vltotalexw as \"FOB\", (select sigla from moeda where p.idmoedafrete = moeda.idmoeda) AS \"Moeda Frete\", "+
        "'0' as \"Frete\", moeda.sigla AS \"Moeda Despesa\",e.vltotalexw * 0.1 AS \"Despesa\",moeda.sigla AS \"Moeda Lucro\",'0' AS \"Lucro\","+
        "moeda.sigla AS \"Moeda Impostos\",'0' AS \"Impostos\",substring (p.nrprocesso, 4,6) as \"Observação\" FROM "+
        "public.processoexportacao e, public.processo p, public.moeda, public.viatransporte, public.cidade, public.uf,"+
        "public.pais, public.embarcacao WHERE p.idmoedamle = moeda.idmoeda AND p.idprocesso = e.idprocesso AND "+
        "(p.idembarcacao is null or p.idembarcacao = embarcacao.idembarcacao) and e.idcidadeorigem = cidade.idcidade AND "+
        "cidade.iduf = uf.iduf and p.idviatransporte = viatransporte.idviatransporte AND e.idpaisdestinofinal = pais.idpais AND P.nrprocesso IN ("+
        exportacao +") and p.idservico in (18,19,20)";
        String caminho = "\\\\bhsvr2\\publica\\Seguro";//args[1];
        System.out.println(caminho);
        Conexao con = new Conexao();
        List<String> listaimp = new ArrayList<String>();
        List<String> listaexp = new ArrayList<String>();
        listaimp = separaProcessos(importacao);
        listaexp = separaProcessos(exportacao);
        con.consultar(con.getCon(), sqlimp, caminho, listaimp, "imp");
        con.consultar(con.getCon(), sqlexp, caminho, listaexp, "exp");

    }

    public static List<String> separaProcessos(String processos){
    Integer i =4;
    List<String> lista = new ArrayList<String>();
    while (i<processos.length()) {
        lista.add(processos.substring(i, i+6));
        i=i+12;
    }
    return lista;
    }
}