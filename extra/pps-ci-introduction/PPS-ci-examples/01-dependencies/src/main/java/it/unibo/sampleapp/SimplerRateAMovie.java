package it.unibo.sampleapp;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.tools.OmdbBuilder;

import static java.lang.System.out;

/**
 * Monolitic application that fetches movie rates.
 */
public final class SimplerRateAMovie {
    private static final String DEFAULT_MOVIE = "Breaking Bad";
    private static final String OMDB_API_KEY = System.getenv("OMDB_API_KEY");

    private SimplerRateAMovie() { }

    /**
     * Launches the application. Expects {@code OMDB_API_KEY} to be a valid environment variable.
     *
     * @param args a string with the movie/series name.
     */
    public static void main(final String[] args) throws OMDBException {
        if (OMDB_API_KEY == null || OMDB_API_KEY.isBlank()) {
            out.println("Invalid OMDB API Key {}, please set a valid API Key as the environment variable OMDB_API_KEY");
            System.exit(1);
        }
        final String[] titles = args.length == 0 ? new String[] { DEFAULT_MOVIE } : args;
        final OmdbApi omdb = new OmdbApi(OMDB_API_KEY);
        for (final var title: titles) {
            final var searchResults = omdb.search(title).getResults();
            for (final var searchResult: searchResults) {
                final var movieHandler = new OmdbBuilder().setApiKey(OMDB_API_KEY)
                    .setPlotFull().setImdbId(searchResult.getImdbID()).build();
                final var movie = omdb.getInfo(movieHandler);
                out.println(
                    "\n=========================\n" + movie.getTitle() + "\nDirected by " + movie.getDirector()
                        + "; " + String.join(", ", movie.getCountries()) + "; " + movie.getYear()
                        + ".\nWritten by " + movie.getWriter() + ".\nCast: " + movie.getActors() + ".\n"
                        + movie.getRuntime() + "; " + movie.getGenre() + ".\nIMDb " + movie.getImdbRating()
                        + " (over " + movie.getImdbVotes() + " votes). Awards: " + movie.getAwards() + ".\n"
                        + movie.getPlot()
                );
            }
        }
    }
}
