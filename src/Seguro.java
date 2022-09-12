

import java.util.Date;

/**
 * seguro
 */

public class Seguro {
 
    private String nrDi;
    private String tipoTransporte;
    private String nomeVeiculo;
    private String paisOrigem;
    private String cidadeOrigem;
    private String paisDestino;
    private String estadoDestino;
    private String estadoOrigem;
    private String cidadeDestino;
    private Date dataSaida; 
    private String embalagem;
    private String resumoMercadoria;
    private String moedaFob;
    private Float valorFob;
    private String moedaFrete;
    private Float valorFrete;
    private String moedaDespesa;
    private float valorDespesa;
    private String moedaLucro;
    private Float valorLucro;
    private String moedaImposto;
    private Float valorImposto;
    private String observacao;

    public String getNrDi() {
        return nrDi;
    }
    public void setNrDi(String nrDi) {
        this.nrDi = nrDi;
    }
    public String getTipoTransporte() {
        return tipoTransporte;
    }
    
    public void setTipoTransporte(String tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }
    public String getNomeVeiculo() {
        return nomeVeiculo;
    }
    public void setNomeVeiculo(String nomeVeiculo) {
        this.nomeVeiculo = nomeVeiculo;
    }
    public String getPaisOrigem() {
        return paisOrigem;
    }
    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }
    public String getCidadeOrigem() {
        return cidadeOrigem;
    }
    public void setCidadeOrigem(String cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }
    public String getPaisDestino() {
        return paisDestino;
    }
    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }
    public String getEstadoDestino() {
        return estadoDestino;
    }
    public void setEstadoDestino(String estadoDestino) {
        this.estadoDestino = estadoDestino;
    }
    public String getCidadeDestino() {
        return cidadeDestino;
    }
    public void setCidadeDestino(String cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }
    public Date getDataSaida() {
        return dataSaida;
    }
    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }
    public String getEmbalagem() {
        return embalagem;
    }
    public void setEmbalagem(String embalagem) {
        this.embalagem = embalagem;
    }
    public String getResumoMercadoria() {
        return resumoMercadoria;
    }
    public void setResumoMercadoria(String resumoMercadoria) {
        this.resumoMercadoria = resumoMercadoria;
    }
    public String getMoedaFob() {
        return moedaFob;
    }
    public void setMoedaFob(String moedaFob) {
        this.moedaFob = moedaFob;
    }
    public Float getValorFob() {
        return valorFob;
    }
    public void setValorFob(Float valorFob) {
        this.valorFob = valorFob;
    }
    public String getMoedaFrete() {
        return moedaFrete;
    }
    public void setMoedaFrete(String moedaFrete) {
        this.moedaFrete = moedaFrete;
    }
    public Float getValorFrete() {
        return valorFrete;
    }
    public void setValorFrete(Float valorFrete) {
        this.valorFrete = valorFrete;
    }
    public String getMoedaDespesa() {
        return moedaDespesa;
    }
    public void setMoedaDespesa(String moedaDespesa) {
        this.moedaDespesa = moedaDespesa;
    }
    public float getValorDespesa() {
        return valorDespesa;
    }
    public void setValorDespesa(float valorDespesa) {
        this.valorDespesa = valorDespesa;
    }
    public String getMoedaLucro() {
        return moedaLucro;
    }
    public void setMoedaLucro(String moedaLucro) {
        this.moedaLucro = moedaLucro;
    }
    public Float getValorLucro() {
        return valorLucro;
    }
    public void setValorLucro(Float valorLucro) {
        this.valorLucro = valorLucro;
    }
    public String getMoedaImposto() {
        return moedaImposto;
    }
    public void setMoedaImposto(String moedaImposto) {
        this.moedaImposto = moedaImposto;
    }
    public Float getValorImposto() {
        return valorImposto;
    }
    public void setValorImposto(Float valorImposto) {
        this.valorImposto = valorImposto;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    public Seguro() {
    }
    public Seguro(String nrDi, String tipoTransporte, String nomeVeiculo, String paisOrigem, String cidadeOrigem,
            String paisDestino, String estadoDestino, String cidadeDestino, Date dataSaida, String embalagem,
            String resumoMercadoria, String moedaFob, Float valorFob, String moedaFrete, Float valorFrete,
            String moedaDespesa, float valorDespesa, String moedaLucro, Float valorLucro, String moedaImposto,
            Float valorImposto, String observacao) {
        this.nrDi = nrDi;
        this.tipoTransporte = tipoTransporte;
        this.nomeVeiculo = nomeVeiculo;
        this.paisOrigem = paisOrigem;
        this.cidadeOrigem = cidadeOrigem;
        this.paisDestino = paisDestino;
        this.estadoDestino = estadoDestino;
        this.cidadeDestino = cidadeDestino;
        this.dataSaida = dataSaida;
        this.embalagem = embalagem;
        this.resumoMercadoria = resumoMercadoria;
        this.moedaFob = moedaFob;
        this.valorFob = valorFob;
        this.moedaFrete = moedaFrete;
        this.valorFrete = valorFrete;
        this.moedaDespesa = moedaDespesa;
        this.valorDespesa = valorDespesa;
        this.moedaLucro = moedaLucro;
        this.valorLucro = valorLucro;
        this.moedaImposto = moedaImposto;
        this.valorImposto = valorImposto;
        this.observacao = observacao;
    }
    public String getEstadoOrigem() {
        return estadoOrigem;
    }
    public void setEstadoOrigem(String estadoOrigem) {
        this.estadoOrigem = estadoOrigem;
    }
    
    
}
    
