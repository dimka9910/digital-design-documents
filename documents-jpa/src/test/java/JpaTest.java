import com.github.dimka9910.documents.dao.CatalogueDao;
import com.github.dimka9910.documents.jpa.daoImpl.CatalogueDaoJpa;
import com.github.dimka9910.documents.jpa.entity.files.catalogues.Catalogue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {

    @Autowired
    CatalogueDaoJpa catalogueDaoJpa;

    @Test
    public void test1() {

        System.out.println("hi");
    }
}
