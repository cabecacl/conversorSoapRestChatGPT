import com.theokanning.openai.completion.CompletionResult;
import controle.ControleArquivoSOAP;
import controle.ControleChatGPT;
import model.ClasseRestController;
import service.ServiceChatGPT;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class IniciarAplicacao {

    private static final String TEXTO_INICIAL_1 = "Crie uma classe RestController com SpringBoot e swagger dessa classe java: \n";
    private static final String TEXTO_INICIAL_2 = "Crie uma documentação OpenAPI yaml dessa classe java: \n";

    public static void main(String[] args) {

        ControleChatGPT controleChatGPT = new ControleChatGPT(new ServiceChatGPT());
        ControleArquivoSOAP controleArquivoSOAP = new ControleArquivoSOAP();

        Scanner scan = new Scanner(System.in);

        System.out.println("Informe o caminho base para gerar os dados:");
        String pathBase = scan.nextLine();

        controleArquivoSOAP.recuperarListaArquivosSOAP(pathBase);

        controleArquivoSOAP.listaArquivos.forEach(file -> {
            try {
                String dadosFile = new String(Files.readAllBytes(file.toPath()));

                dadosFile = controleArquivoSOAP.removerComentariosClasse(dadosFile);

                CompletionResult result = controleChatGPT.request(TEXTO_INICIAL_1 + dadosFile);
                ClasseRestController classeRestController = controleArquivoSOAP.recuperarESalvarClasseRestController(result.getChoices().get(0).getText(), file);

                result = controleChatGPT.request(TEXTO_INICIAL_2 + classeRestController.getConteudo());
                controleArquivoSOAP.recuperarESalvarDocumentacaoSwagger(result.getChoices().get(0).getText(), file, classeRestController);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}