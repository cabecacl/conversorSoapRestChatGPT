import com.theokanning.openai.completion.CompletionResult;
import controle.ControleArquivoSOAP;
import controle.ControleChatGPT;
import model.ClasseRestController;
import model.Message;
import service.ServiceChatGPT;
import service.ServiceGpt4ApiClientRestTemplate;
import util.UtilProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class IniciarAplicacao2 {

//    private static final String TEXTO_INICIAL_1 = "Crie uma classe RestController com SpringBoot e swagger dessa classe java: \n";
    private static final String TEXTO_INICIAL_1 = "Crie apenas uma classe RestController com SpringBoot e swagger dessa classe java, " +
        "porém você deve escreva todos os metodos na classe criada: \n";
    private static final String TEXTO_INICIAL_2 = "Crie uma documentação OpenAPI yaml dessa classe java: \n";

    public static void main(String[] args) {

        // Crie uma instância do cliente da API do ChatGPT
        ServiceGpt4ApiClientRestTemplate chatGptApiClient = new ServiceGpt4ApiClientRestTemplate();

        // Criar instancia do controleSoap
        ControleChatGPT controleChatGPT = new ControleChatGPT(new ServiceChatGPT());
        ControleArquivoSOAP controleArquivoSOAP = new ControleArquivoSOAP();

        // Recuperar o caminho dos dados
        Scanner scan = new Scanner(System.in);

        System.out.println("Informe o caminho base para gerar os dados:");
        String pathBase = scan.nextLine();
//        String pathBase = "C:\\Projetos\\WSDL\\wsdl_faturaSecuritytv";

        controleArquivoSOAP.recuperarListaArquivosSOAP(pathBase);
        controleArquivoSOAP.listaArquivos.forEach(file -> {
            try {
                //Recupera os dados do arquivo
                String dadosFile = new String(Files.readAllBytes(file.toPath()));

                //Remove os comentarios da classe
                dadosFile = controleArquivoSOAP.removerComentariosClasse(dadosFile);


                Message msn1 = new Message();
                msn1.setRole("system");
                msn1.setContent("Eu sou uma IA muito detalhista e sempre gosto de fazer tudo por completo passando todos os pontos, assim trabalho convertendo código SOAP Java para micro serviço com Spring Boot e Swagger gerando apenas uma classe RestController tendo dos os métodos analisados convertidos, sem deixar passar nenhum, mesmo que tenha que criar as classes de modelo eu vou gerar todos os métodos na integra sem esquecer de nenhum");

                Message msn2 = new Message();
                msn2.setRole("user");
                msn2.setContent(TEXTO_INICIAL_1 + dadosFile);

                List<Message> msnTeste = new ArrayList<Message>();
                msnTeste.add(msn1);
                msnTeste.add(msn2);

                List<Message> messages = criarMessage(TEXTO_INICIAL_1 + dadosFile);

                model.CompletionResult result = chatGptApiClient.getChatCompletion(msnTeste);

                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                System.out.println(dadosFile);
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

                System.out.println("############################################################");
                result.getChoices().forEach(item -> System.out.println(item.getMessage().getContent()));
                System.out.println("############################################################");

//                ClasseRestController classeRestController = controleArquivoSOAP.recuperarESalvarClasseRestController(
//                        result.getChoices().get(0).getMessage().getContent(), file);
//
//                messages = criarMessage(TEXTO_INICIAL_2 + classeRestController.getConteudo());
//                result = chatGptApiClient.getChatCompletion(messages);
//
//                controleArquivoSOAP.recuperarESalvarDocumentacaoSwagger(result.getChoices().get(0).getMessage().getContent(), file, classeRestController);

                System.out.println("Acabou o fluxo de conversão, verifique a pasta!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    public static List<Message> criarMessage(String texto){

        Message msn = new Message();
        msn.setRole("user");
        msn.setContent(texto);

        List<Message> messges = new ArrayList<Message>();
        messges.add(msn);

        return messges;

    }
}