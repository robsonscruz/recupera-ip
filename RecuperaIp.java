import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class RecuperaIp
{
    public static void main(String[] args) throws AddressException, MessagingException {
        System.out.println("Analisando IP externo");
        
        Parameters param = new Parameters();
        param.loadJson();
        param.processIpExterno();
        
        String resposta = "Tudo certo, ainda temos o mesmo ip";
        if (!param.ip.equals(param.ipExterno.toString())) {
        	resposta = "Devemos enviar para " +param.email+ " o novo IP " +param.ipExterno;
        	
        	param.ip = param.ipExterno;
        	Email email = new Email();
        	if (email.sendIp(param)) {
        		param.save();
        		
        		resposta += "\r\nE-mail enviado com sucesso!";
        	}
        }
        
        System.out.println(resposta);
    }
}