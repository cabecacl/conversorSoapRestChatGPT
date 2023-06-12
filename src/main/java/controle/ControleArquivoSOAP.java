package controle;

import model.ClasseRestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ControleArquivoSOAP {

    public List<File> listaArquivos;

    public ControleArquivoSOAP(){
        this.listaArquivos = new ArrayList<File>();
    }

    /**
     * Metodo utilizado para listar todos oa arquivos ObjectFactory.java e dai seguir para o processo de migração para Api Rest com SpringBoot
     * @param pathBase
     */
    public void recuperarListaArquivosSOAP(String pathBase){
        File file = new File(pathBase);
        File files[] = file.listFiles();
        for (int i = 0; files.length > i; i++){
            File f = files[i];

            if(f.isDirectory()){
                this.recuperarListaArquivosSOAP(f.getPath());
            }else{
                if(f.getName().equals("ObjectFactory.java")){
                    this.listaArquivos.add(f);
                }
            }
        }
    }

    /**
     * Metodo utilizado para remover comentarios da classe
     * @param texto
     * @return
     */
    public String removerComentariosClasse(String texto){
        while (texto.contains("/**")){
            int indexInicial = texto.indexOf("/**");
            int indexFinal = texto.indexOf("*/") + 2;

            String remover = texto.substring(indexInicial, indexFinal);

            texto = texto.replace(remover, "");
        }
        return texto;
    }

    /**
     * Metodo retornar o conteudo da classe e ao mesmo tempo salvar o arquivo .java do restController
     * @param texto
     * @param arquivo
     * @return
     */
    public ClasseRestController recuperarESalvarClasseRestController(String texto, File arquivo){

        ClasseRestController classe = new ClasseRestController();

        classe.setConteudo(texto.substring(texto.indexOf("@RestController")));
        int indexNomeClassInicial = classe.getConteudo().indexOf("class") + 6;

        classe.setNome(classe.getConteudo().substring(indexNomeClassInicial, indexNomeClassInicial + (classe.getConteudo().substring(indexNomeClassInicial).indexOf(" "))));

        String path = arquivo.getParent() + "\\" + classe.getNome() + ".java";
        try {
            Files.write(Paths.get(path), classe.getConteudo().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return classe;
    }

    /**
     * Metodo utilizado para recuperar a documentação swagger e salvar o arquivo .yaml
     * @param texto
     * @param arquivo
     * @param classeRestController
     * @return
     */
    public String recuperarESalvarDocumentacaoSwagger(String texto, File arquivo, ClasseRestController classeRestController){

        String conteudo = texto.substring(texto.indexOf("openapi"));

        String path = arquivo.getParent() + "\\" + classeRestController.getNome() + ".yaml";
        try {
            Files.write(Paths.get(path), conteudo.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return conteudo;
    }

}
