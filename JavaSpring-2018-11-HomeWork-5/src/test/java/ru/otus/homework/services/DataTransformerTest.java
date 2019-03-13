package ru.otus.homework.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.shell.table.TableModel;
import ru.otus.homework.models.TestEmpty;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.otus.outside.utils.TestData.TEST_STRING_ARRAY_TEST_ID;
import static ru.otus.outside.utils.TestData.TEST_STRING_ARRAY_WITH_NULL;

@DisplayName("Class DataTransformerTest")
class DataTransformerTest
{
    private DataTransformer<TestService, TestEmpty> transformer;

    private TestService service;

    public static void assertEqualsTableModel(TableModel expected, TableModel model)
    {
        assertEquals(expected.getRowCount(), model.getRowCount());
        assertEquals(expected.getColumnCount(), model.getColumnCount());
        for (int i = 0; i < expected.getRowCount(); ++i) {
            for (int j = 0; j < expected.getColumnCount(); ++j) {
                assertEquals(expected.getValue(i,j), model.getValue(i,j));
            }
        }
    }

    @Test
    @DisplayName("is instantiated with new DataTransformerTest()")
    void isInstantiatedWithNew()
    {
        new DataTransformer<>(null);
    }

    private DataTransformer<TestService,TestEmpty> createTransformerMock()
    {
        service = mock(TestService.class);
        return new DataTransformer<>(service);
    }

    private DataTransformer<TestService,TestEmpty> createTransformerTestService()
    {
        service = new TestService();
        return new DataTransformer<>(service);
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            transformer = createTransformerMock();
        }

        @Test
        @DisplayName("injected values in AuthorsServiceImpl()")
        void defaults()
        {
            assertThat(transformer).hasFieldOrPropertyWithValue("service", service);
        }

        @DisplayName("transform empty list")
        @Test
        void  transformList_empty_mock()
        {
            when(service.findAll()).thenReturn(new ArrayList<>());
            when(service.getHeader()).thenReturn(TEST_STRING_ARRAY_TEST_ID);

            String[][] array = new String[][]{service.getHeader()};
            TableModel expected = DataTransformer.create(array).build().getModel();
            TableModel model = transformer.transformList(service.findAll()).build().getModel();
            assertEqualsTableModel(expected, model);
        }
    }

    @Nested
    @DisplayName("when TestService")
    class WhenTestService
    {
        @BeforeEach
        void createNew()
        {
            transformer = createTransformerTestService();
        }

        @Test
        @DisplayName("injected values in TestService()")
        void defaults()
        {
            assertThat(transformer).hasFieldOrPropertyWithValue("service", service);
        }

        @DisplayName("transform null link to DataSet")
        @Test
        void transformDataSet_null()
        {
            assertThrows(AssertionError.class, () -> transformer.transformDataSet(null));
        }

        @DisplayName("transform TestEmpty DataSet")
        @Test
        void transformDataSet_success()
        {
            String[][] array = new String[][]{service.getHeader(), TEST_STRING_ARRAY_WITH_NULL};
            TableModel expected = DataTransformer.create(array).build().getModel();
            TableModel model = transformer.transformDataSet(service.findById(0L)).build().getModel();
            assertEqualsTableModel(expected, model);
        }

        @DisplayName("transform null link to List")
        @Test
        void transformList_null()
        {
            assertThrows(AssertionError.class, () -> transformer.transformList(null));
        }

        @DisplayName("transform TestService")
        @Test
        void  transformList_success()
        {
            String[][] array = new String[][]{service.getHeader(), TEST_STRING_ARRAY_WITH_NULL};
            TableModel expected = DataTransformer.create(array).build().getModel();
            TableModel model = transformer.transformList(service.findAll()).build().getModel();
            assertEqualsTableModel(expected, model);
        }
    }
}