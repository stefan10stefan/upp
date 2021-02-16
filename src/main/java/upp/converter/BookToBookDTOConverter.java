package upp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import upp.dto.BookDTO;
import upp.model.Book;

@Component
public class BookToBookDTOConverter implements Converter<Book, BookDTO> {
    @Override
    public BookDTO convert(Book book) {

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setLink(book.getLink());

        return bookDTO;
    }
}
