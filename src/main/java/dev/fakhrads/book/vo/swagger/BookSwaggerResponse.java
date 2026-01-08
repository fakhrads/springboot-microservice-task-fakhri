package dev.fakhrads.book.vo.swagger;

import dev.fakhrads.book.dto.DtoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "BookVoResponse", description = "Standard response wrapper with BookResponse payload")
public class BookSwaggerResponse extends DtoResponse<BookSwaggerResponse> {
}
