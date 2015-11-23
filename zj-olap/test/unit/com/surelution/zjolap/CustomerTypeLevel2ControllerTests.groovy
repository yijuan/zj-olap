package com.surelution.zjolap



import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerTypeLevel2Controller)
@Mock(CustomerTypeLevel2)
class CustomerTypeLevel2ControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/customerTypeLevel2/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.customerTypeLevel2InstanceList.size() == 0
        assert model.customerTypeLevel2InstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.customerTypeLevel2Instance != null
    }

    void testSave() {
        controller.save()

        assert model.customerTypeLevel2Instance != null
        assert view == '/customerTypeLevel2/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/customerTypeLevel2/show/1'
        assert controller.flash.message != null
        assert CustomerTypeLevel2.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/customerTypeLevel2/list'

        populateValidParams(params)
        def customerTypeLevel2 = new CustomerTypeLevel2(params)

        assert customerTypeLevel2.save() != null

        params.id = customerTypeLevel2.id

        def model = controller.show()

        assert model.customerTypeLevel2Instance == customerTypeLevel2
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/customerTypeLevel2/list'

        populateValidParams(params)
        def customerTypeLevel2 = new CustomerTypeLevel2(params)

        assert customerTypeLevel2.save() != null

        params.id = customerTypeLevel2.id

        def model = controller.edit()

        assert model.customerTypeLevel2Instance == customerTypeLevel2
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/customerTypeLevel2/list'

        response.reset()

        populateValidParams(params)
        def customerTypeLevel2 = new CustomerTypeLevel2(params)

        assert customerTypeLevel2.save() != null

        // test invalid parameters in update
        params.id = customerTypeLevel2.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/customerTypeLevel2/edit"
        assert model.customerTypeLevel2Instance != null

        customerTypeLevel2.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/customerTypeLevel2/show/$customerTypeLevel2.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        customerTypeLevel2.clearErrors()

        populateValidParams(params)
        params.id = customerTypeLevel2.id
        params.version = -1
        controller.update()

        assert view == "/customerTypeLevel2/edit"
        assert model.customerTypeLevel2Instance != null
        assert model.customerTypeLevel2Instance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/customerTypeLevel2/list'

        response.reset()

        populateValidParams(params)
        def customerTypeLevel2 = new CustomerTypeLevel2(params)

        assert customerTypeLevel2.save() != null
        assert CustomerTypeLevel2.count() == 1

        params.id = customerTypeLevel2.id

        controller.delete()

        assert CustomerTypeLevel2.count() == 0
        assert CustomerTypeLevel2.get(customerTypeLevel2.id) == null
        assert response.redirectedUrl == '/customerTypeLevel2/list'
    }
}
