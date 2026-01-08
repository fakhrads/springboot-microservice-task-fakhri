package dev.fakhrads.book.vo.swagger;

import dev.fakhrads.book.vo.BookVoResponse;
import dev.fakhrads.book.dto.DtoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "BookListResponseDto", description = "Standard response wrapper with List<BookResponse> payload")
public class BookSwaggerListResponse extends DtoResponse<List<BookVoResponse>> {}
