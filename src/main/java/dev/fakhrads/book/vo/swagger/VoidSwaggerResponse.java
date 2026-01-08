package dev.fakhrads.book.vo.swagger;

import dev.fakhrads.book.dto.DtoResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VoidResponseDto", description = "Standard response wrapper with null payload")
public class VoidSwaggerResponse extends DtoResponse<Object> {}
