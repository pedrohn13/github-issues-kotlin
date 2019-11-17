import br.com.phneto.kotlinissues.api.GitHubIssuesService
import br.com.phneto.kotlinissues.api.GithubIssueRemoteDataSource
import org.koin.dsl.module

val appModule = module {

    single { GithubIssueRemoteDataSource() }
    factory { GitHubIssuesService() }

}
