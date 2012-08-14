package com.siu.android.athismons.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.view.MenuItem;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.siu.android.andutils.activity.tracker.TrackedSherlockActivity;
import com.siu.android.athismons.R;
import com.siu.android.athismons.dao.model.Card;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class CardDetailActivity extends TrackedSherlockActivity {

    public static final String EXTRA = "extra";

    private TextView title, building, street, cityPostalCode, addressComplement, contact, contactLabel, website, email, phoneLabel, phone1, phone2;
    private ImageView picture;
    private Button locateButton;

    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.card_detail_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.card_detail_title);
        building = (TextView) findViewById(R.id.card_detail_building);
        street = (TextView) findViewById(R.id.card_detail_street);
        cityPostalCode = (TextView) findViewById(R.id.card_detail_postalcode_city);
        addressComplement = (TextView) findViewById(R.id.card_detail_complement);
        contactLabel = (TextView) findViewById(R.id.card_detail_contact_label);
        contact = (TextView) findViewById(R.id.card_detail_contact);
        website = (TextView) findViewById(R.id.card_detail_website);
        email = (TextView) findViewById(R.id.card_detail_email);
        phoneLabel = (TextView) findViewById(R.id.card_detail_phone_label);
        phone1 = (TextView) findViewById(R.id.card_detail_phone_1);
        phone2 = (TextView) findViewById(R.id.card_detail_phone_2);
        picture = (ImageView) findViewById(R.id.card_detail_picture);

        locateButton = (Button) findViewById(R.id.card_detail_locate);
        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = new StringBuilder("geo:0,0?q=")
                        .append(card.getLatitude())
                        .append(",")
                        .append(card.getLongitude())
                        .toString();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(location));

                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(CardDetailActivity.this, R.string.error_no_maps, Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (null != savedInstanceState) {
            card = (Card) savedInstanceState.get("card");
        } else {
            card = (Card) getIntent().getExtras().get(EXTRA);
        }

        initCard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("card", card);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initCard() {
        title.setText(card.getTitle());

        if (StringUtils.isNotEmpty(card.getPicture())) {
            UrlImageViewHelper.setUrlDrawable(picture, card.getPicture());
        } else {
            picture.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(card.getBuilding())) {
            building.setText(card.getBuilding());
        } else {
            building.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(card.getStreet())) {
            street.setText(card.getStreet());
        } else {
            street.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(card.getCity())) {
            cityPostalCode.setText(card.getCity() + " " + card.getPostalCode());
        } else {
            cityPostalCode.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(card.getAddressComplement())) {
            addressComplement.setText(card.getAddressComplement());
        } else {
            addressComplement.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(card.getContact())) {
            contact.setText(card.getContact());
        } else {
            contact.setVisibility(View.GONE);
            contactLabel.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(card.getPhone1())) {
            phone1.setText(card.getPhone1());

            if (StringUtils.isNotEmpty(card.getPhone2())) {
                phone2.setText(card.getPhone2());
            } else {
                phone2.setVisibility(View.GONE);
            }
        } else {
            phoneLabel.setVisibility(View.GONE);
            phone1.setVisibility(View.GONE);
            phone2.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(card.getEmail())) {
            email.setText(getString(R.string.card_detail_email, card.getEmail()));
        } else {
            email.setVisibility(View.GONE);
        }

        if (StringUtils.isNotEmpty(card.getWebsite())) {
            website.setText(getString(R.string.card_detail_website, card.getWebsite()));
        } else {
            website.setVisibility(View.GONE);
        }
    }
}
