package db;

/**
 * Serve para lançar um Exceção personalizada, pegando a mensagem de uma Exeção
 * padrão e a definindo de acordo com a sua aplicação
 *
 * @author P. Godoy
 */
public class DbIntegrityException extends RuntimeException {

    public DbIntegrityException(String msg) {
        super(msg);
    }
}
