package com.yandex.mapkitdemo;

import android.Manifest;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.yandex.mapkit.*;
import com.yandex.mapkit.directions.navigation.SpeedLimitsRules;
import com.yandex.mapkit.geometry.BoundingBox;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.geometry.SubpolylineHelper;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.*;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.*;
import com.yandex.mapkit.transport.TransportFactory;
import com.yandex.mapkit.transport.masstransit.*;
import com.yandex.mapkit.transport.masstransit.Session;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.Error;
import com.yandex.runtime.i18n.I18nManagerFactory;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * This is a basic example that displays a map and sets camera focus on the target location.
 * Note: When working on your projects, remember to request the required permissions.
 */
public class MapActivity extends AppCompatActivity implements Session.RouteListener,
        GeoObjectTapListener, InputListener, UserLocationObjectListener,
        com.yandex.mapkit.search.Session.SearchListener,
        CameraListener, SuggestSession.SuggestListener {
    /**
     * Replace "your_api_key" with a valid developer key.
     * You can get it at the https://developer.tech.yandex.ru/ website.
     */
    private final Point TARGET_LOCATION = new Point(53.9, 27.5667);

    private final Point ROUTE_START_LOCATION = new Point(53.894234, 27.561915);
    private final Point ROUTE_END_LOCATION = new Point(53.892540, 27.557012);

    private final BoundingBox BOUNDING_BOX = new BoundingBox(
            new Point(ROUTE_START_LOCATION.getLatitude() - 0.2, ROUTE_START_LOCATION.getLongitude() - 0.2),
            new Point(ROUTE_END_LOCATION.getLatitude() + 0.2, ROUTE_END_LOCATION.getLongitude() + 0.2));
    private final SuggestOptions SEARCH_OPTIONS = new SuggestOptions().setSuggestTypes(
            SuggestType.GEO.value |
                    SuggestType.BIZ.value |
                    SuggestType.TRANSIT.value);

    private boolean routing = false;
    private Point startPoint;
    private Point endPoint;


    private MapView mapView;
    private MasstransitRouter mtRouter;
    private MapObjectCollection mapObjects;
    private UserLocationLayer userLocationLayer;
    private MapKit mapKit;
    private com.yandex.mapkit.search.Session searchSession;
    private SearchManager searchManager;
    private SuggestSession suggestSession;
    private List<String> suggestResultRoute;
    private ArrayAdapter<String> resultAdapterRoute;


    private boolean isFind = false;

    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout linearLayoutBSheet;
    private ListView listView;
    private TextView textCommon, textDescription, textNumberTransport, textSubtitle, textTimeArrived;
    private ImageButton btnClose;
    private ImageView iconTransp;
    private ImageButton findLocation;
    private ImageButton routeBtn;
    private Button closeBtnRoute;
    private Button buildBtnRoute;
    private AlertDialog dialog;
    private AutoCompleteTextView startPointRoute;
    private AutoCompleteTextView endPointRoute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        MapKitFactory.setApiKey("a305ff24-d0df-4871-9a52-0ae434368133");
        MapKitFactory.initialize(this);

        setContentView(R.layout.map);
        checkPermission();
        getSupportActionBar().hide();
        mapView = findViewById(R.id.mapview);
        init();
        initRouteDialog();

        // And to show what can be done with it, we move the camera to the center of Saint Petersburg.
        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
        mapView.getMap().addTapListener(this);
        mapView.getMap().addInputListener(this);
        mapView.getMap().deselectGeoObject();

        mapObjects = mapView.getMap().getMapObjects().addCollection();
    }


    private void initRouteDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Маршрут");

        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        builder.setView(view);
        dialog = builder.create();
        startPointRoute = view.findViewById(R.id.edit_start_point_route);
        endPointRoute = view.findViewById(R.id.edit_end_point_route);
        suggestResultRoute = new ArrayList<>();
        resultAdapterRoute = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                suggestResultRoute);
        startPointRoute.setAdapter(resultAdapterRoute);
        startPointRoute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                requestSuggest(s.toString());
            }
        });

        endPointRoute.setAdapter(resultAdapterRoute);
        endPointRoute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                requestSuggest(s.toString());

            }
        });


        buildBtnRoute = view.findViewById(R.id.build_route);
        buildBtnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Start1 route");
                if (startPointRoute.getText() != null && endPointRoute.getText() != null) {
                    System.out.println("Start2 route");
                    routing = true;
                    System.out.println(startPointRoute.getText());
                    submitQuery(startPointRoute.getText().toString());
                    System.out.println(endPointRoute.getText());
                    submitQuery(endPointRoute.getText().toString());
                }

//                drawRoutes();
//                System.out.println("start point");
//                System.out.println(startPoint.getLatitude());
//                System.out.println(startPoint.getLongitude());
//                System.out.println("end point");
//                System.out.println(endPoint.getLatitude());
//                System.out.println(endPoint.getLongitude());
//                if (startPoint != null && endPoint != null) {
//                    drawRoutes();
//                } else {
//                    commonError("cannot find start, end point for draw route");
//                }

                dialog.dismiss();
            }
        });
        closeBtnRoute = view.findViewById(R.id.close_btn_route);
        closeBtnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }

    private void init() {

        mapKit = MapKitFactory.getInstance();
        SearchFactory.initialize(this);
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setObjectListener(this);
        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED);
        suggestSession = searchManager.createSuggestSession();
        mtRouter = TransportFactory.getInstance().createMasstransitRouter();


        this.linearLayoutBSheet = findViewById(R.id.bottomSheet);
        this.bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBSheet);
        this.listView = findViewById(R.id.list_transp);
        this.textCommon = findViewById(R.id.text_common);
        this.textDescription = findViewById(R.id.text_description);
        this.textNumberTransport = findViewById(R.id.number_transp);
        this.textSubtitle = findViewById(R.id.subtitle);
        this.textTimeArrived = findViewById(R.id.text_time);
        this.btnClose = findViewById(R.id.btn_close);
        this.iconTransp = findViewById(R.id.icon_transp);
        this.findLocation = findViewById(R.id.find_loc_btn);
        this.routeBtn = findViewById(R.id.route_btn);

        findLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFind) {
                    isFind = true;
                    userLocationLayer.setVisible(true);
                    userLocationLayer.setHeadingEnabled(true);
//                    userLocationLayer.setObjectListener(MapActivity.this);
                } else {
                    userLocationLayer.setVisible(false);
                    userLocationLayer.setHeadingEnabled(false);
                }
            }

        });

//        MyListAdapter adapter=new MyListAdapter(this, maintitle, subtitle,imgid);
//        listView.setAdapter(adapter);


        bottomSheetBehavior.setPeekHeight(0);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                bottomSheetBehavior.setPeekHeight(0);
            }
        });

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setPeekHeight(300);
                }
                if (i == BottomSheetBehavior.STATE_COLLAPSED && bottomSheetBehavior.getPeekHeight() == 300) {
                    bottomSheetBehavior.setPeekHeight(100);
                }
                if (i == BottomSheetBehavior.STATE_HIDDEN) {
                    mapView.getMap().deselectGeoObject();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

    }

    public void setListOfTransport() {

    }

    public void checkPermission() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MapActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MapActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\\n\\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
        I18nManagerFactory.setLocale(Locale.getDefault().getLanguage());
    }

    @Override
    public void onMasstransitRoutes(@NonNull List<Route> list) {
        mapObjects.clear();
        if (list.size() > 0) {
            for (Section section : list.get(0).getSections()) {
                drawSection(
                        section.getMetadata().getData(),
                        SubpolylineHelper.subpolyline(
                                list.get(0).getGeometry(), section.getGeometry()));
            }
        }
    }

    @Override
    public void onMasstransitRoutesError(@NonNull Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();

    }


    private void drawSectionTransit(SectionMetadata.SectionData data,
                                    Polyline geometry) {

        PolylineMapObject polylineMapObject = mapObjects.addPolyline(geometry);

        if (data.getTransports() != null) {

            for (Transport transport : data.getTransports()) {
                if (transport.getLine().getStyle() != null) {
                    polylineMapObject.setStrokeColor(
                            transport.getLine().getStyle().getColor() | 0xFF000000
                    );
                    return;
                }
            }
        }
    }

    private void drawSection(SectionMetadata.SectionData data,
                             Polyline geometry) {

        PolylineMapObject polylineMapObject = mapObjects.addPolyline(geometry);

        if (data.getTransports() != null) {

            for (Transport transport : data.getTransports()) {

                if (transport.getLine().getStyle() != null) {
                    polylineMapObject.setStrokeColor(
                            transport.getLine().getStyle().getColor() | 0xFF000000
                    );
                    return;
                }
            }

            HashSet<String> knownVehicleTypes = new HashSet<>();
            knownVehicleTypes.add("bus");
            knownVehicleTypes.add("tramway");
            knownVehicleTypes.add("minibus");
            for (Transport transport : data.getTransports()) {
                String sectionVehicleType = getVehicleType(transport, knownVehicleTypes);
                if (sectionVehicleType.equals("bus")) {
                    polylineMapObject.setStrokeColor(0xFF00FF00);  // Green
                    return;
                } else if (sectionVehicleType.equals("tramway")) {
                    polylineMapObject.setStrokeColor(0xFFFF0000);  // Red
                    return;
                }else if (sectionVehicleType.equals("minibus")){
                    polylineMapObject.setStrokeColor(0xFF00FF00);  // Green
                    return;
                }
            }
            polylineMapObject.setStrokeColor(0xFF0000FF);  // Blue
        } else {

            polylineMapObject.setStrokeColor(0xFF000000);  // Black
        }
    }

    private String getVehicleType(Transport transport, HashSet<String> knownVehicleTypes) {

        System.out.println("getVehicleTypes");
        for (String type : transport.getLine().getVehicleTypes()) {
            System.out.println(type);
            if (knownVehicleTypes.contains(type)) {

                return type;
            }
        }
        return "unknown";
    }

    @Override
    public boolean onObjectTap(@NonNull GeoObjectTapEvent geoObjectTapEvent) {
        final GeoObjectSelectionMetadata selectionMetadata = geoObjectTapEvent
                .getGeoObject()
                .getMetadataContainer()
                .getItem(GeoObjectSelectionMetadata.class);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setPeekHeight(300);


        System.out.println(geoObjectTapEvent.getGeoObject().getName());
        System.out.println(geoObjectTapEvent.getGeoObject().getGeometry().get(0).getPoint().getLatitude());
        System.out.println(geoObjectTapEvent.getGeoObject().getGeometry().get(0).getPoint().getLongitude());


        if (selectionMetadata != null) {
            mapView.getMap().selectGeoObject(selectionMetadata.getId(), selectionMetadata.getLayerId());
        }

        return selectionMetadata != null;
    }

    @Override
    public void onMapTap(@NonNull Map map, @NonNull Point point) {
        mapView.getMap().deselectGeoObject();

    }

    @Override
    public void onMapLongTap(@NonNull Map map, @NonNull Point point) {

    }

    private void submitQuery(String query) {
        searchSession = searchManager.submit(
                query,
                VisibleRegionUtils.toPolygon(mapView.getMap().getVisibleRegion()),
                new SearchOptions(),
                this);
    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float) (mapView.getWidth() * 0.5), (float) (mapView.getHeight() * 0.5)),
                new PointF((float) (mapView.getWidth() * 0.5), (float) (mapView.getHeight() * 0.83)));

        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_arrow));
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }

    @Override
    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
        if (b) {
            submitQuery(startPointRoute.getText().toString());
            submitQuery(endPointRoute.getText().toString());
        }
    }

    public void drawRoutes() {

        MasstransitOptions options = new MasstransitOptions(
                new ArrayList<>(),
                new ArrayList<>(),
                new TimeOptions());
        List<RequestPoint> points = new ArrayList<>();
        System.out.println("start point");
        System.out.println(startPoint.getLatitude());
        System.out.println(startPoint.getLongitude());
        System.out.println("end point");
        System.out.println(endPoint.getLatitude());
        System.out.println(endPoint.getLongitude());
        if (startPoint != null && endPoint != null) {
            points.add(new RequestPoint(startPoint, RequestPointType.WAYPOINT, null));
            points.add(new RequestPoint(endPoint, RequestPointType.WAYPOINT, null));
        } else {
            commonError("Draw Route failed, something wrong");
        }

        mtRouter.requestRoutes(points, options, this);
        mapView.getMap().move(
                new CameraPosition(startPoint, 15.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
    }

    @Override
    public void onSearchResponse(@NonNull Response response) {
        System.out.println("Start onSearchResponse");
        MapObjectCollection mapObjects = mapView.getMap().getMapObjects();
//        mapObjects.clear();
        System.out.println(routing);
        System.out.println("text" + response.getMetadata().getRequestText());
        System.out.println("type" + response.getMetadata().getDisplayType());
        if (response.getCollection().getChildren().size() > 0) {
            System.out.println(response.getCollection().getChildren().get(0).getObj().getGeometry().get(0).getPoint().getLongitude());
            System.out.println(response.getCollection().getChildren().get(0).getObj().getGeometry().get(0).getPoint().getLatitude());
        }
        for (GeoObjectCollection.Item searchResult : response.getCollection().getChildren()) {
            Point resultLocation = searchResult.getObj().getGeometry().get(0).getPoint();
            if (resultLocation != null && !routing) {
                mapObjects.addPlacemark(
                        resultLocation,
                        ImageProvider.fromResource(this, R.drawable.map_point));
            } else if (resultLocation != null && routing) {

                if (startPointRoute.getText().toString().equals(response.getMetadata().getRequestText())) {
                    System.out.println("set start point" + resultLocation.getLatitude()+" "+resultLocation.getLongitude());
                    startPoint = new Point(resultLocation.getLatitude(), resultLocation.getLongitude());
                }
                if (endPointRoute.getText().toString().equals(response.getMetadata().getRequestText())) {
                    System.out.println("set end point"  + resultLocation.getLatitude()+" "+resultLocation.getLongitude());
                    endPoint = new Point(resultLocation.getLatitude(), resultLocation.getLongitude());
                }
            }
        }
        if (startPoint != null && endPoint != null) {
            drawRoutes();
        }
    }

    public void commonError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchError(@NonNull Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(@NonNull List<SuggestItem> list) {
        suggestResultRoute.clear();
        resultAdapterRoute.clear();
        for (int i = 0; i < Math.min(5, list.size()); i++) {
            suggestResultRoute.add(list.get(i).getDisplayText());

        }
        System.out.println("On Response");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            suggestResultRoute.forEach(System.out::println);
        }
        resultAdapterRoute.addAll(suggestResultRoute);
        resultAdapterRoute.notifyDataSetChanged();
    }

    @Override
    public void onError(@NonNull Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void requestSuggest(String query) {
        System.out.println("requestSuggest");
        suggestSession.suggest(query, BOUNDING_BOX, SEARCH_OPTIONS, this);
    }
}
