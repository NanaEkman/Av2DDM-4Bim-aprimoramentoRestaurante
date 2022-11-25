package ddm.nana.av2ddm_4bim_aprimoramentorestaurante;

public class Item {

    private String chave;
    private String mesa;
    private String item;
    private String produto;
    private boolean atendido;

    public Item(){}

    public Item(String chave, String mesa, String item, String produto) {
        this.chave = chave;
        this.mesa = mesa;
        this.item = item;
        this.produto = produto;
        this.atendido = false;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

}

