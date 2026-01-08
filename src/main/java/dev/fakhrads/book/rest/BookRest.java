package dev.fakhrads.book.rest;

import dev.fakhrads.book.vo.BookVoCreateRequest;
import dev.fakhrads.book.vo.BookVoPatchRequest;
import dev.fakhrads.book.vo.BookVoResponse;
import dev.fakhrads.book.vo.BookVoUpdateRequest;
import dev.fakhrads.book.service.BookService;
import dev.fakhrads.book.dto.DtoHelper;
import dev.fakhrads.book.dto.DtoResponse;
import dev.fakhrads.book.vo.swagger.BookSwaggerListResponse;
import dev.fakhrads.book.vo.swagger.BookSwaggerResponse;
import dev.fakhrads.book.vo.swagger.VoidSwaggerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Book Management CRUD APIs")
public class BookRest {

    private final BookService service;

    public BookRest(BookService service) {
        this.service = service;
    }

    @Operation(
            summary = "Add a new book",
            description = "Creates a new book record and returns the created book wrapped in DtoResponse."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Book created",
                    content = @Content(schema = @Schema(implementation = BookSwaggerResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = BookSwaggerListResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Duplicate/constraint error (e.g., ISBN already exists)",
                    content = @Content(schema = @Schema(implementation = VoidSwaggerResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<DtoResponse<BookVoResponse>> create(
            @Valid
            @RequestBody(
                    description = "Book payload to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = BookVoCreateRequest.class),
                            examples = @ExampleObject(
                                    name = "CreateBookExample",
                                    value = "{\n  \"title\": \"Clean Code\",\n  \"author\": \"Robert C. Martin\",\n  \"isbn\": \"9780132350884\",\n  \"publishedDate\": \"2008-08-01\"\n}"
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody BookVoCreateRequest req,
            HttpServletRequest httpReq
    ) {
        BookVoResponse data = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoHelper.ok("Book created", data, httpReq.getRequestURI()));
    }

    @Operation(
            summary = "Get all books",
            description = "Returns list of all books wrapped in DtoResponse."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(schema = @Schema(implementation = BookSwaggerListResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<DtoResponse<List<BookVoResponse>>> findAll(HttpServletRequest httpReq) {
        List<BookVoResponse> data = service.findAll();
        return ResponseEntity.ok(DtoHelper.ok("OK", data, httpReq.getRequestURI()));
    }

    @Operation(
            summary = "Get a book by ID",
            description = "Returns a single book by its ID wrapped in DtoResponse."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(schema = @Schema(implementation = BookSwaggerResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(schema = @Schema(implementation = VoidSwaggerResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DtoResponse<BookVoResponse>> findById(
            @Parameter(description = "Book ID", example = "1", required = true)
            @PathVariable Long id,
            HttpServletRequest httpReq
    ) {
        BookVoResponse data = service.findById(id);
        return ResponseEntity.ok(DtoHelper.ok("OK", data, httpReq.getRequestURI()));
    }

    @Operation(
            summary = "Update a book by ID (full update)",
            description = "Performs full replacement update on a book record."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book updated",
                    content = @Content(schema = @Schema(implementation = BookSwaggerResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = VoidSwaggerResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(schema = @Schema(implementation = VoidSwaggerResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Duplicate/constraint error (e.g., ISBN already exists)",
                    content = @Content(schema = @Schema(implementation = VoidSwaggerResponse.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<DtoResponse<BookVoResponse>> update(
            @Parameter(description = "Book ID", example = "1", required = true)
            @PathVariable Long id,
            @Valid
            @RequestBody(
                    description = "Full update payload for a book",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = BookVoUpdateRequest.class),
                            examples = @ExampleObject(
                                    name = "UpdateBookExample",
                                    value = "{\n  \"title\": \"Clean Code (Updated)\",\n  \"author\": \"Robert C. Martin\",\n  \"isbn\": \"9780132350884\",\n  \"publishedDate\": \"2008-08-01\"\n}"
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody BookVoUpdateRequest req,
            HttpServletRequest httpReq
    ) {
        BookVoResponse data = service.update(id, req);
        return ResponseEntity.ok(DtoHelper.ok("Book updated", data, httpReq.getRequestURI()));
    }

    @Operation(
            summary = "Partial update of a book (PATCH)",
            description = "Updates one or more fields of the book. Only non-null fields will be updated."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book patched",
                    content = @Content(schema = @Schema(implementation = BookSwaggerResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(schema = @Schema(implementation = VoidSwaggerResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Duplicate/constraint error (e.g., ISBN already exists)",
                    content = @Content(schema = @Schema(implementation = VoidSwaggerResponse.class))
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<DtoResponse<BookVoResponse>> patch(
            @Parameter(description = "Book ID", example = "1", required = true)
            @PathVariable Long id,
            @RequestBody(
                    description = "Partial update payload (any subset of fields)",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = BookVoPatchRequest.class),
                            examples = @ExampleObject(
                                    name = "PatchBookExample",
                                    value = "{\n  \"title\": \"Clean Code (Patched)\"\n}"
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody BookVoPatchRequest req,
            HttpServletRequest httpReq
    ) {
        BookVoResponse data = service.patch(id, req);
        return ResponseEntity.ok(DtoHelper.ok("Book patched", data, httpReq.getRequestURI()));
    }

    @Operation(
            summary = "Delete a book by ID",
            description = "Deletes a book record by ID. Returns standard response wrapper with null payload."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book deleted",
                    content = @Content(schema = @Schema(implementation = VoidSwaggerResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(schema = @Schema(implementation = VoidSwaggerResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DtoResponse<Object>> delete(
            @Parameter(description = "Book ID", example = "1", required = true)
            @PathVariable Long id,
            HttpServletRequest httpReq
    ) {
        service.delete(id);
        return ResponseEntity.ok(DtoHelper.ok("Book deleted", null, httpReq.getRequestURI()));
    }
}
