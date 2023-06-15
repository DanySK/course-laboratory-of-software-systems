package it.unibo.sampleapp;

import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.SearchResults;
import com.omertron.omdbapi.tools.OmdbBuilder;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class RateAMovie {
    private static final String DEFAULT_MOVIE = "Breaking Bad";
    private static final String OMDB_API_KEY = System.getenv("OMDB_API_KEY");
    private static final Logger LOGGER = LoggerFactory.getLogger(RateAMovie.class);

    private RateAMovie() {
    }

    public static void main(final String[] args) {
        if (OMDB_API_KEY == null || OMDB_API_KEY.isBlank()) {
            LOGGER.error(
                "Invalid OMDB API Key {}, please set a valid API Key as the environment variable OMDB_API_KEY",
                OMDB_API_KEY
            );
            System.exit(1);
        }
        final String[] titles = args.length == 0 ? new String[] { DEFAULT_MOVIE } : args;
        final OmdbApi omdb = new OmdbApi(OMDB_API_KEY);
        Arrays.stream(titles)
            .map(Unchecked.function(omdb::search)) // Exceptions become RuntimeExceptions
            .map(SearchResults::getResults) // get all results
            .flatMap(List::stream) // as a flat stream
            // then get more details
            .map(Unchecked.function(movie ->
                new OmdbBuilder().setApiKey(OMDB_API_KEY).setPlotFull().setImdbId(movie.getImdbID()).build())
            )
            .map(Unchecked.function(omdb::getInfo))
            .forEach(movie ->
                LOGGER.info(
                    "\n=========================\n{}\n"
                        + "Directed by {}; {}; {}\n"
                        + "Written by {}\n"
                        + "Cast: {}\n"
                        + "{} {}\n"
                        + "IMDb {} (over {} votes), Rotten Tomatoes {}. {}\n"
                        + "{}\n"
                        + "IMDb handle: {}",
                    movie.getTitle(),
                    movie.getDirector(),
                    String.join(", ", movie.getCountries()),
                    movie.getYear(),
                    movie.getWriter(),
                    movie.getActors(),
                    movie.getRuntime(),
                    movie.getGenre(),
                    movie.getImdbRating(),
                    movie.getImdbVotes(),
                    movie.getTomatoRating(),
                    movie.getAwards(),
                    movie.getPlot(),
                    movie.getImdbID()
                )
            );
    }
}
