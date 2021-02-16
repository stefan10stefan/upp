package upp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import upp.dto.BookDTO;
import upp.model.Book;

@Component
public class BookDTOToBookConverter implements Converter<BookDTO, Book> {
    @Override
    public Book convert(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setLink(bookDTO.getLink());
        return book;
    }
}
