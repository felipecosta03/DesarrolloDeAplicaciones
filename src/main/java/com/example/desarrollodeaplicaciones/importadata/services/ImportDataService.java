package com.example.desarrollodeaplicaciones.importadata.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.desarrollodeaplicaciones.configs.files.IFilesStorage;
import com.example.desarrollodeaplicaciones.dtos.MovieCreationDTO;
import com.example.desarrollodeaplicaciones.dtos.PersonDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusMovieDTO;
import com.example.desarrollodeaplicaciones.dtos.StatusPersonDTO;
import com.example.desarrollodeaplicaciones.importadata.utils.ImportDataMapper;
import com.example.desarrollodeaplicaciones.importdata.models.Page;
import com.example.desarrollodeaplicaciones.services.IImportDataServices;
import com.example.desarrollodeaplicaciones.services.IMovieService;
import com.example.desarrollodeaplicaciones.services.IPersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ImportDataService implements IImportDataServices {

  private final IMovieService movieService;
  private final IPersonService personService;
  private final IPageServices pageServices;
	private final RestTemplate restTemplate;
	private HttpHeaders headers;

	public ImportDataService (IMovieService movieService, IPersonService personService, IFilesStorage fileStorage, IPageServices pageServices) {
		this.movieService = movieService;
		this.personService = personService;
		this.pageServices = pageServices;
		this.restTemplate = new RestTemplate();
		this.headers = new HttpHeaders();
		this.headers.set("Authorization", getTokenAuthorization());
		
	}
	@Override
	public String importData() {
		JsonNode resultsNodeMovies = obtainMovies();
		Iterator<JsonNode> resultsIterator = resultsNodeMovies.elements();
		
		int contador = 0;
		while (resultsIterator.hasNext() && contador < 10) {
	    JsonNode resultNode = resultsIterator.next();
	    String idMovieToImport = resultNode.get("id").asText();
	    MovieCreationDTO movieCreationDto = ImportDataMapper.importDataJsonMovieToMovie(resultNode);
	    JsonNode resultsNodeDetails = obtainDetails(idMovieToImport);
	    movieCreationDto.setGenre(resultsNodeDetails.get("genres").elements().next().get("name").asText());
	    movieCreationDto.setDuration(resultsNodeDetails.get("runtime").asInt());
	    StatusMovieDTO statusMovieDTO = movieService.add(movieCreationDto);	    
	    
	    importActors(idMovieToImport, movieCreationDto, statusMovieDTO.getMovieDto().getId());
	    importImages(idMovieToImport, statusMovieDTO.getMovieDto().getId());
	    
	    contador++;
		}
		return resultsNodeMovies.asText();
	}
	
	
	
	private void importActors(String idTheMovieDBToImport, MovieCreationDTO movieCreationDto, Long movieId) {
		 JsonNode resultsNodeActors = obtainActors(idTheMovieDBToImport);
	    Iterator<JsonNode> resultsIteratorActors = resultsNodeActors.elements();
	    int contador2 = 0;
	    while (resultsIteratorActors.hasNext() && contador2 < 10) {
	    	JsonNode resultNodeActor = resultsIteratorActors.next();
	    	PersonDTO actor = ImportDataMapper.importDataActorToPerson(resultNodeActor);
	    	StatusPersonDTO statusPersonDTO = personService.add(actor);
	    	movieCreationDto.getActors().add(statusPersonDTO.getPersonDto().getId());
	    	contador2++;
	    }
	    movieService.update(movieId, movieCreationDto);
	}
	
	private void importImages(String idTheMovieDBToImport, Long movieId) {
		JsonNode resultsNodeImages = obtainImages(idTheMovieDBToImport);
    Iterator<JsonNode> resultsIteratorImages = resultsNodeImages.elements();
    int contador3 = 0;
    while (resultsIteratorImages.hasNext() && contador3 < 5) {
    	JsonNode resultNodeImages = resultsIteratorImages.next();
    	String relativeURL = resultNodeImages.get("file_path").asText();
    	downloadImage(relativeURL);
    	uploadImage(relativeURL,movieId);
    	contador3++;
    }
	}
	
	
	private JsonNode obtainMovies( ) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		Page page = pageServices.increment();
		ResponseEntity<String> responseEntity = restTemplate.exchange(this.getPopularWithPagination(String.valueOf(page.getNumero())), HttpMethod.GET, entity, String.class);
		String responseBody = responseEntity.getBody();	
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			System.out.println(responseBody);
			JsonNode rootNode = objectMapper.readTree(responseBody);
			return rootNode.get("results");
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private JsonNode obtainDetails(String movieId) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(this.getDetails(movieId), HttpMethod.GET, entity, String.class);
		String responseBody = responseEntity.getBody();	
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readTree(responseBody);
		}	 catch (JsonMappingException e) {
					e.printStackTrace();
					return null;
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return null;
				}
	}
	
	private JsonNode obtainActors(String movieId) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(this.getActors(movieId), HttpMethod.GET, entity, String.class);
		String responseBody = responseEntity.getBody();	
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode rootNode = objectMapper.readTree(responseBody);
			return rootNode.get("cast");
		}	 catch (JsonMappingException e) {
					e.printStackTrace();
					return null;
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return null;
				}
	}	
	
	private JsonNode obtainImages(String movieId) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(this.getImages(movieId), HttpMethod.GET, entity, String.class);
		String responseBody = responseEntity.getBody();	
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode rootNode = objectMapper.readTree(responseBody);
			return rootNode.get("backdrops");
		}	 catch (JsonMappingException e) {
					e.printStackTrace();
					return null;
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return null;
				}
	}
	
	private void downloadImage(String relativeURL) {
		String imageUrl = ImportDataResources.BASE_URL_IMAGE.getResources() + relativeURL;
    String destinationFile = "/home/cristian/images_movie" + relativeURL;

    try {
        URL url = new URL(imageUrl);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();

        FileOutputStream outputStream = new FileOutputStream(destinationFile);

        int bytesRead;
        byte[] buffer = new byte[1024];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        System.out.println("Imagen descargada con éxito.");

        inputStream.close();
        outputStream.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
	}
	
	public void uploadImage(String relativeURL, Long movieId) {
		 String filePath = "/home/cristian/images_movie" + relativeURL;
     byte[] fileBytes = null;
     try {
         File file = new File(filePath);
         FileInputStream fis = new FileInputStream(file);
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         byte[] buf = new byte[1024];
         int bytesRead;
         while ((bytesRead = fis.read(buf)) != -1) {
             bos.write(buf, 0, bytesRead);
         }
         fis.close();
         bos.close();

         fileBytes = bos.toByteArray();
         movieService.updateMovieImageFromServer(movieId, fileBytes);

         } catch (IOException e) {
     					e.printStackTrace();
         }
	}
	
	
	private String getTokenAuthorization() {
		return "Bearer " + ImportDataResources.TOKEN.getResources(); 
	}
	
	private String getUrl(ImportDataResources resource) {
		return ImportDataResources.BASE_URL.getResources() + resource.getResources();
	}
	
	private String getPopularWithPagination(String page) {
		return getUrl(ImportDataResources.POPULAR) + page;
	}
	
	private String getDetails(String movieId) {
		return getUrl(ImportDataResources.DETAILS).replace("movie_id", movieId);
	}
	
	private String getActors(String movieId) {
		return getUrl(ImportDataResources.ACTORS).replace("movie_id", movieId);
	}
	
	private String getImages(String movieId) {
		return getUrl(ImportDataResources.IMAGES).replace("movie_id", movieId);
	}

}
