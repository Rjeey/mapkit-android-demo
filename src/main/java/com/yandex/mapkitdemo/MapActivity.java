package com.yandex.mapkitdemo;

import android.Manifest;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
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
import com.yandex.mapkit.directions.driving.DrivingSectionMetadata;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.geometry.SubpolylineHelper;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.*;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.BusinessObjectMetadata;
import com.yandex.mapkit.search.ToponymObjectMetadata;
import com.yandex.mapkit.transport.TransportFactory;
import com.yandex.mapkit.transport.masstransit.*;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.mapkitdemo.utils.list.MyListAdapter;
import com.yandex.runtime.Error;
import com.yandex.runtime.i18n.I18nManagerFactory;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.util.*;

/**
 * This is a basic example that displays a map and sets camera focus on the target location.
 * Note: When working on your projects, remember to request the required permissions.
 */
public class MapActivity extends AppCompatActivity implements Session.RouteListener,
        GeoObjectTapListener, InputListener, UserLocationObjectListener {
    /**
     * Replace "your_api_key" with a valid developer key.
     * You can get it at the https://developer.tech.yandex.ru/ website.
     */
    private final Point TARGET_LOCATION = new Point(53.9, 27.5667);

    private final Point ROUTE_START_LOCATION = new Point(53.894234, 27.561915);
    private final Point ROUTE_END_LOCATION = new Point(53.892540, 27.557012);


    private MapView mapView;
    private MasstransitRouter mtRouter;
    private MapObjectCollection mapObjects;
    private UserLocationLayer userLocationLayer;
    private MapKit mapKit;
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
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >=21){
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

        // And to show what can be done with it, we move the camera to the center of Saint Petersburg.
        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
        mapView.getMap().addTapListener(this);
        mapView.getMap().addInputListener(this);
        mapView.getMap().deselectGeoObject();

        mapObjects = mapView.getMap().getMapObjects().addCollection();

        MasstransitOptions options = new MasstransitOptions(
                new ArrayList<>(),
                new ArrayList<>(),
                new TimeOptions());
        List<RequestPoint> points = new ArrayList<RequestPoint>();
        points.add(new RequestPoint(ROUTE_START_LOCATION, RequestPointType.WAYPOINT, null));
        points.add(new RequestPoint(ROUTE_END_LOCATION, RequestPointType.WAYPOINT, null));
        mtRouter = TransportFactory.getInstance().createMasstransitRouter();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Маршрут");

        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        closeBtnRoute = view.findViewById(R.id.close_btn_route);
        closeBtnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        builder.setView(view);
        dialog = builder.create();
        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void init() {

        mapKit = MapKitFactory.getInstance();
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setObjectListener(this);

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
        findLocation = findViewById(R.id.find_loc_btn);
        this.routeBtn = findViewById(R.id.route_btn);

        findLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFind) {
                    isFind = true;
                    userLocationLayer.setVisible(true);
                    userLocationLayer.setHeadingEnabled(true);
//                    userLocationLayer.setObjectListener(MapActivity.this);
                }else{
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
                if(i == BottomSheetBehavior.STATE_COLLAPSED && bottomSheetBehavior.getPeekHeight()==300){
                    bottomSheetBehavior.setPeekHeight(100);
                }
                if(i == BottomSheetBehavior.STATE_HIDDEN){
                    mapView.getMap().deselectGeoObject();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

    }

    public void setListOfTransport(){

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
        // Activity onStop call must be passed to both MapView and MapKit instance.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        // Activity onStart call must be passed to both MapView and MapKit instance.
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
        I18nManagerFactory.setLocale(Locale.getDefault().getLanguage());
    }

    @Override
    public void onMasstransitRoutes(@NonNull List<Route> list) {
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

    private void drawSection(SectionMetadata.SectionData data,
                             Polyline geometry) {
        // Draw a section polyline on a map
        // Set its color depending on the information which the section contains
        PolylineMapObject polylineMapObject = mapObjects.addPolyline(geometry);
        // Masstransit route section defines exactly one on the following
        // 1. Wait until public transport unit arrives
        // 2. Walk
        // 3. Transfer to a nearby stop (typically transfer to a connected
        //    underground station)
        // 4. Ride on a public transport
        // Check the corresponding object for null to get to know which
        // kind of section it is
        if (data.getTransports() != null) {
            // A ride on a public transport section contains information about
            // all known public transport lines which can be used to travel from
            // the start of the section to the end of the section without transfers
            // along a similar geometry
            for (Transport transport : data.getTransports()) {
                // Some public transport lines may have a color associated with them
                // Typically this is the case of underground lines
                if (transport.getLine().getStyle() != null) {
                    polylineMapObject.setStrokeColor(
                            // The color is in RRGGBB 24-bit format
                            // Convert it to AARRGGBB 32-bit format, set alpha to 255 (opaque)
                            transport.getLine().getStyle().getColor() | 0xFF000000
                    );
                    return;
                }
            }
            // Let us draw bus lines in green and tramway lines in red
            // Draw any other public transport lines in blue
            HashSet<String> knownVehicleTypes = new HashSet<>();
            knownVehicleTypes.add("bus");
            knownVehicleTypes.add("tramway");
            for (Transport transport : data.getTransports()) {
                String sectionVehicleType = getVehicleType(transport, knownVehicleTypes);
                if (sectionVehicleType.equals("bus")) {
                    polylineMapObject.setStrokeColor(0xFF00FF00);  // Green
                    return;
                } else if (sectionVehicleType.equals("tramway")) {
                    polylineMapObject.setStrokeColor(0xFFFF0000);  // Red
                    return;
                }
            }
            polylineMapObject.setStrokeColor(0xFF0000FF);  // Blue
        } else {
            // This is not a public transport ride section
            // In this example let us draw it in black
            polylineMapObject.setStrokeColor(0xFF000000);  // Black
        }
    }

    private String getVehicleType(Transport transport, HashSet<String> knownVehicleTypes) {
        // A public transport line may have a few 'vehicle types' associated with it
        // These vehicle types are sorted from more specific (say, 'histroic_tram')
        // to more common (say, 'tramway').
        // Your application does not know the list of all vehicle types that occur in the data
        // (because this list is expanding over time), therefore to get the vehicle type of
        // a public line you should iterate from the more specific ones to more common ones
        // until you get a vehicle type which you can process
        // Some examples of vehicle types:
        // "bus", "minibus", "trolleybus", "tramway", "underground", "railway"
        for (String type : transport.getLine().getVehicleTypes()) {
            if (knownVehicleTypes.contains(type)) {
                return type;
            }
        }
        return null;
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

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.5)),
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.83)));

        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_arrow));

        CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();

//        pinIcon.setIcon(
//                "icon",
//                ImageProvider.fromResource(this, R.drawable.icon),
//                new IconStyle().setAnchor(new PointF(0f, 0f))
//                        .setRotationType(RotationType.ROTATE)
//                        .setZIndex(0f)
//                        .setScale(1f)
//        );
//
//        pinIcon.setIcon(
//                "pin",
//                ImageProvider.fromResource(this, R.drawable.search_result),
//                new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
//                        .setRotationType(RotationType.ROTATE)
//                        .setZIndex(1f)
//                        .setScale(0.5f)
//        );
//
//        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);

    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }
}
